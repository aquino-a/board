<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Registration</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
        <script src="/js/post.js"></script>
        <script>


            $(document).ready(function () {
                $('#id-check').click(function (event) {
                    var username = $('#username').val();
                    window.open('register/check?username=' + username, "", "width=400,height=100");

                });

                $('#submit').click(function (event) {

                    data = $('#pwd').val();
                    var len = data.length;

                    if (len < 1) {
                        alert("Password cannot be blank");
                        // Prevent form submission
                        event.preventDefault();
                    }

                    if ($('#pwd').val() !== $('#pwdc').val()) {
                        alert("Password and Confirm Password don't match");
                        // Prevent form submission
                        event.preventDefault();
                    }
                    var fullAddress = $('#full-road').val();

                    if (fullAddress.length < 5 || $('#post-code').val().length < 3) {
                        alert("You must input an address.");
                        event.preventDefault();
                    }

                    $("#info").submit();

                });

                $('#post-code-select').click(function (event) {
                    postCodeSelect();
                });





            });





        </script>
    </head>
    <body>

        <div class="container">
            <h2>Registration</h2>
            <form class="form-horizontal" action="/users/register" method="post" id="info">
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="username">Username:</label>
                    <div class="col-sm-4">
                        <input type="text"  class="form-control" id="username" placeholder="Enter username" name="username"/>
                    </div> 
                    <span class="input-group-btn">
                        <button type="button" class="btn btn-default" id="id-check" >ID Check</button>
                    </span>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="pwd">Password:</label>
                    <div class="col-sm-4">          
                        <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="password"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="pwdc">Password confirm:</label>
                    <div class="col-sm-4">          
                        <input type="password" class="form-control" id="pwdc" placeholder="Confirm Password"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="name">Name:</label>
                    <div class="col-sm-4">          
                        <input type="text" class="form-control" id="name" placeholder="Name" name="name"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="age">Age:</label>
                    <div class="col-sm-4">          
                        <input type="text" class="form-control" id="age" placeholder="Age" name="age"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="email">Email:</label>
                    <div class="col-sm-6">
                        <input type="email"  class="form-control" id="email" placeholder="Enter email" name="email"/>
                    </div>
                </div>


                <div class="form-group">
                    <label class="control-label col-sm-2" for="age">Postal Code:</label>
                    <div class="col-sm-2">          
                        <input type="text" class="form-control" id="post-code" placeholder="pc" name="address.postCode"/>
                    </div>
                    <span class="input-group-btn">
                        <button type="button" class="btn btn-default" id="post-code-select" >Find</button>
                    </span>




                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="age">Address:</label>
                    <div class="col-sm-4">          
                        <input type="text"  class="form-control" id="full-road"  name="address.fullAddress"/>
                        <input type="text" class="form-control" id="jibun"  name="address.jibunAddress"/>
                    </div>
                </div>
                <div class="form-group">        
                    <div class="col-sm-offset-2 col-sm-4">
                        <button type="submit" class="btn btn-default" id="submit">Submit</button>
                    </div>
                </div>

            </form>
        </div>

    </body>

</html>
