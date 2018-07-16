/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.board.oauth;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Fail;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.logging.Logger;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
/**
 *
 * @author b005
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class OauthTest {

    @Autowired
    MockMvc mockMvc;
    
    private static Logger logger = Logger.getLogger(OauthTest.class);

    @Test
    public void noTokenUnauthorized() throws Exception {
        mockMvc.perform(post("/posts/new"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(delete("/posts/2"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void postTokenAndGetPostAndDelete() throws Exception {
        String accessToken = obtainAccessToken("john", "pass");
        
        MockMultipartFile file2 = new MockMultipartFile("files", "orig.txt", null, "hey".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("files", "orig1.txt", null, "baby".getBytes());
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/posts/new")
                .file(file1).file(file2)
                .header("Authorization", "Bearer " + accessToken)
                .param("text", "Baby, Baby, Baby"))
                .andExpect(status().isCreated());
        
        String str = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String id = jsonParser.parseMap(str).get("id").toString();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/posts/" + id ))
                .andExpect(status().isOk());
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/" + id))
                .andExpect(status().isUnauthorized());
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/" + id)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
        
        mockMvc.perform(
                MockMvcRequestBuilders.get("/posts/" + id ))
                .andExpect(status().isNotFound());

    }
    
    @Test
    public void postTokenAndGetPostAndAdminDelete() throws Exception {
        String accessToken = obtainAccessToken("john", "pass");
        String accessToken2 = obtainAccessToken("polly", "pass");
        String accessTokenAdmin = obtainAccessToken("alex", "pass");
        
        MockMultipartFile file2 = new MockMultipartFile("files", "orig.txt", null, "hey".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("files", "orig1.txt", null, "baby".getBytes());
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/posts/new")
                .file(file1).file(file2)
                .header("Authorization", "Bearer " + accessToken)
                .param("text", "Baby, Baby, Baby"))
                .andExpect(status().isCreated());
        
        String str = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String id = jsonParser.parseMap(str).get("id").toString();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/posts/" + id ))
                .andExpect(status().isOk());
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/" + id))
                .andExpect(status().isUnauthorized());
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/" + id)
                .header("Authorization", "Bearer " + accessToken2))
                .andExpect(status().isForbidden());
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/" + id)
                .header("Authorization", "Bearer " + accessTokenAdmin))
                .andExpect(status().isOk());
        
        mockMvc.perform(
                MockMvcRequestBuilders.get("/posts/" + id ))
                .andExpect(status().isNotFound());

    }
    
    @Test
    public void getImage() throws Exception {
        String accessToken = obtainAccessToken("john", "pass");
        String fileName = "orig1.txt";
        String fileContent = "baby";
        MockMultipartFile file1 = new MockMultipartFile("files", fileName, null, fileContent.getBytes());
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/posts/new")
                .file(file1)
                .header("Authorization", "Bearer " + accessToken)
                .param("text", "Baby, Baby, Baby"))
                .andExpect(status().isCreated());
        String result1 = result.andReturn().getResponse().getContentAsString();
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(result1);
        String id = node.findValue("id").asText();
        
        
        byte[] byteResult = mockMvc.perform(MockMvcRequestBuilders.get("/posts/john/images/" + fileName))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();
        Assertions.assertThat(byteResult).isEqualTo(fileContent.getBytes());
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/" + id)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }
    
    @Test
    public void getPosts() throws Exception {
        int size = 2;
        String posts = mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                .param("size", String.valueOf(size)).param("page","4"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(posts);
        Assertions.assertThat(node.findValue("content").size()).isEqualTo(size);
        
        JSONObject object = new JSONObject(posts);
        Assertions.assertThat(object.getJSONArray("content").length()).isEqualTo(size);
        
        String count = mockMvc.perform(MockMvcRequestBuilders.get("/posts/count"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        try {
            Long.parseLong(count);
        } catch (NumberFormatException e) {
            Fail.fail("Didnt get a long");
        }
    }    
    

    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
//        params.add("client_id", "fooClientIdPassword");s
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                        .params(params)
                        .with(httpBasic("acme", "secret"))
                        .accept("application/json;charset=UTF-8"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

}
