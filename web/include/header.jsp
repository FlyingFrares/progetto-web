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
      <h1><a href="#top"><span class="logo_colour">Carni</span>Padane</a></h1>
      <h2>Il valore del prodotto artigianale</h2>
    </div>
    <img src="images/piglet.png" id="logo_img">
  </div>

  <form name="logoutForm" action="Dispatcher" method="post">
    <input type="hidden" name="controllerAction" value ="Home.logout"/> <!-- menuActiveLink.logout -->
  </form>

  <nav id="menubar"><!-- Defining the navigation menu -->
    <ul id="menu">
      <li <%=menuActiveLink.equals("Home")?"class=\"selected\"":""%>>
        <a href="Dispatcher?controllerAction=Home.view">Home</a>
      </li>
      <li <%=menuActiveLink.equals("Category")?"class=\"selected\"":""%>>
        <a href="Dispatcher?controllerAction=Category.view">Prodotti</a>
      </li>

      <%if (loggedOn) {%>
      <%if (loggedUser.getAdmin().equals("S")) {%>
      <li <%=menuActiveLink.equals("Orders")?"class=\"selected\"":""%>>
        <a href="Dispatcher?controllerAction=Orders.adminView">Ordini</a>
      </li>
      <%} else {%>
      <li <%=menuActiveLink.equals("Orders")?"class=\"selected\"":""%>>
        <a href="Dispatcher?controllerAction=Orders.view">Ordini</a>
      </li>
      <%}%>
      <% if (loggedUser.getAdmin().equals("S")) {%>
      <li <%=menuActiveLink.equals("Admin")?"class=\"selected\"":""%>>
        <a href="Dispatcher?controllerAction=Admin.view">Utenti</a>
      </li>
      <%}%>
      <li><a href="javascript:logoutForm.submit()">Logout</a></li>
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
      <%}%>

      <%if (!loggedOn) {%>
      <!-- < Form per il login > -->
      <section id="login" class="clearfix">
        <form name="logonForm" action="Dispatcher" method="post">
          <input type="text" id="username" placeholder="Username" name="username" maxlength="40" required>
          <input type="password" id="password" placeholder="Password" name="password" maxlength="40" required>
          <input type="hidden" name="controllerAction" value="Home.logon"/> <!-- menuActiveLink.logon -->
          <button type="submit" value="Ok">Login</button>
        </form>
      </section>
      <%}else{%>
      <h1 style="color: #2E3138">Bentornato <%=loggedUser.getNome()%></h1>
      <%}%>
    </ul>
  </nav>
</header>