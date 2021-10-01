<script>

    /* Tecnica standard per la validazione dei campi Username e Password */

    function headerOnLoadHandler() {
        var usernameTextField = document.querySelector("#username");
        var usernameTextFieldMsg = "Lo username \xE8 obbligatorio.";
        var passwordTextField = document.querySelector("#password");
        var passwordTextFieldMsg = "La password \xE8 obbligatoria.";

        if (usernameTextField != undefined && passwordTextField != undefined ) {
            usernameTextField.setCustomValidity(usernameTextFieldMsg);
            usernameTextField.addEventListener("change", function () {
                this.setCustomValidity(this.validity.valueMissing ? usernameTextFieldMsg : "");
            });
            passwordTextField.setCustomValidity(passwordTextFieldMsg);
            passwordTextField.addEventListener("change", function () {
                this.setCustomValidity(this.validity.valueMissing ? passwordTextFieldMsg : "");
            });
        }
    }
</script>

<header class="clearfix"><!-- Defining the header section of the page -->


  <div id="logo">
    <div id="logo_text">
      <h1><a href="index.html"><span class="logo_colour">Carni</span>Padane</a></h1>
      <h2>Il valore del prodotto artigianale</h2>
    </div>
    <img src="images/piglet.png" id="logo_img">
  </div>

  <form name="logoutForm" action="Dispatcher" method="post">
    <input type="hidden" name="controllerAction" value="Home.logout"/>
  </form>

  <nav id="menubar"><!-- Defining the navigation menu -->
    <ul id="menu">
      <li <%=menuActiveLink.equals("Home")?"class=\"selected\"":""%>>
        <a href="Dispatcher?controllerAction=Home.view">Home</a>
      </li>
      <li <%=menuActiveLink.equals("Prodotti")?"class=\"selected\"":""%>>
        <a href="Dispatcher?controllerAction=Category.view">Prodotti</a>
      </li>
      <li <%=menuActiveLink.equals("Carrello")?"class=\"selected\"":""%>>
        <a href="Dispatcher?controllerAction=Cart.view">
          <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-shopping-cart" margin="10 20 30 40" width="48" height="48" viewBox="0 0 24 24" stroke-width="1.5" stroke="#2E3138" fill="none" stroke-linecap="round" stroke-linejoin="round">
            <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
            <circle cx="9" cy="19" r="2" />
            <circle cx="17" cy="19" r="2" />
            <path d="M3 3h2l2 12a3 3 0 0 0 3 2h7a3 3 0 0 0 3 -2l1 -7h-15.2" />
          </svg>
        </a>
      </li>

      <%if (loggedOn) {%>
      <li <%=menuActiveLink.equals("Ordini")?"class=\"active\"":""%>>
        <a href="Dispatcher?controllerAction=Home.view">Ordini</a>
      </li>
      <!--%if(loggedUser.getAdmin().equals("c")){%-->
      <li><a href="javascript:logoutForm.submit()">Logout</a></li>
      <%}%>

      <%if (!loggedOn) {%>
      <!-- < Form per il login > -->
      <section id="login" class="clearfix">
        <form name="logonForm" action="Dispatcher" method="post">
          <input type="text" id="username" placeholder="Username" name="username" maxlength="40" required>
          <input type="password" id="password" placeholder="Password" name="password" maxlength="40" required>
          <input type="hidden" name="controllerAction" value="Home.logon"/>
          <button type="submit" value="Ok">Login</button>
        </form>
      </section>
      <%}else{%>
      <h1>Bentornato <%=loggedUser.getAdmin()%></h1>
      <%}%>
    </ul>
  </nav>
</header>