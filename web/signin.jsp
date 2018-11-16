<!DOCTYPE html>
<html lang="en" href="style.css" href="//fonts.googleapis.com/css?family=Play">
<head>
    <link href="style.css" rel="stylesheet" type="text/css" media="all">
    <meta charset="UTF-8">
    <title>Cinema</title>
</head>

<body>
<!-Link map for header-->
<map name="header-links">
    <area target="" alt="browse" title="browse" href="Browse.html" coords="227,204,347,237" shape="rect">
    <area target="" alt="account" title="account" href="signin.jsp" coords="353,203,473,237" shape="rect">
    <area target="" alt="home" title="home" href="index.jsp" coords="154,79,532,168" shape="rect">
</map>
<div id="wrapper">
    <div class="header">
        <div id="header-img"><img usemap="#header-links" src="Images/retroheader3.png"/></div>
    </div>

    <div class="content">
        <div class="content-text">
            <div class="contentBox">
                <form name="login" action="SigninServlet" method="post">
                    <p>Sign up and book movie tickets now!</p>
                    <p>
                        Email: <input type="text" placeholder="Enter Email" name="email" required>
                    </p>
                    <p>
                        Password: <input type="password" placeholder="Enter Password" name="password" required>
                    </p>
                    <p>
                        <button type="submit" value="login">Sign In</button>
                    </p>
                    <p>
                        Don't have an account? <a href="registration.jsp">Make one now!</a>
                    </p>
                </form>
            </div>
        </div>
    </div>


</div>

</body>
</html>