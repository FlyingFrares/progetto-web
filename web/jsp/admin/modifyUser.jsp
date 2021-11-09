<%@page session="false"%>
<%@page import="model.mo.Utente"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");   /* Stringa passata dal Controller */
  String menuActiveLink = "Admin";
  Utente utente = (Utente) request.getAttribute("utente");
  String action = (utente != null) ? "modify" : "create";
%>

<!DOCTYPE html>
<html>
<head>
  <%@include file="/include/htmlHead.jsp"%>
  <style>
    .row {
      display: -ms-flexbox; /* IE10 */
      display: flex;
      -ms-flex-wrap: wrap; /* IE10 */
      flex-wrap: wrap;
    }
    .col {
      -ms-flex: 50%; /* IE10 */
      flex: 50%;
      width: 300px;
    }

    .container {
      background-color: #f2f2f2;
      padding: 5px 20px 15px 20px;
      border: 1px solid lightgrey;
      border-radius: 3px;
    }

    input[type=text], select {
      margin-bottom: 20px;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 3px;
    }

    label {
      margin-bottom: 10px;
      display: block;
    }

    .btn {
      background-color: #FFC2D6;
      color: black;
      padding: 12px;
      margin: 10px 0;
      border: none;
      width: 100%;
      border-radius: 3px;
      cursor: pointer;
      font-size: 17px;
      justify-content: center;
    }

    .btn:hover {
      background-color:#CDB4DB;
    }

    .delbtn {
      background-color: #99CEFF;
    }
  </style>
</head>
<body>
<%@include file="/include/header.jsp"%>
<main class="clearfix">
  <div id="site_content">
    <div id="content">
      <div class="row">
        <div class="container">
          <form name="createModForm" action="Dispatcher" method="post">
            <div class="row">

              <div class="col">
                <h3>Login</h3>
                <label for="uname">Username</label>
                <input type="text" id="uname" name="username" value="<%=(action.equals("modify")) ? utente.getUsername() : ""%>" required>
                <label for="pword">Password</label>
                <input type="text" id="pword" name="password" value="<%=(action.equals("modify")) ? utente.getPassword() : ""%>" required>
                <label for="permission">Permessi</label>
                <select id="permission" name="permessi">
                  <option value="S" <%if (action.equals("modify") && utente.getAdmin().equals("S")){%> selected <%}%>>Admin</option>
                  <option value="N" <%if (action.equals("modify") && utente.getAdmin().equals("N")){%> selected <%}%>>Utente</option>
                  <%if (action.equals("modify")) {%>
                  <option value="B" <%if (action.equals("modify") && utente.getAdmin().equals("B")){%> selected <%}%>>Bloccato</option>
                  <%}%>
                </select>
              </div>

              <div class="col">
                <h3>Anagrafica</h3>
                <label for="mail">E-Mail</label>
                <input type="text" id="mail" name="mail" value="<%=(action.equals("modify")) ? utente.getEmail() : ""%>" required>
                <label for="name">Nome</label>
                <input type="text" id="name" name="name" value="<%=(action.equals("modify")) ? utente.getNome() : ""%>" required>
                <label for="surname">Cognome</label>
                <input type="text" id="surname" name="surname" value="<%=(action.equals("modify")) ? utente.getCognome() : ""%>" required>
              </div>

            </div>
            <%if (action.equals("modify")) {%>
            <input type="hidden" name="userID" value="<%=utente.getUserID()%>">
            <%}%>
            <input type="hidden" name="controllerAction" value="Admin.<%=action%>User">
            <input type="submit" value="conferma" class="btn">
          </form>
          <form name="discardForm" action="Dispatcher" method="post">
            <input type="hidden" name="controllerAction" value="Admin.view">
            <input type="submit" value="annulla" class="btn delbtn">
          </form>
        </div>
      </div>
    </div>
  </div>
</main>
<%@include file="/include/footer.jsp"%>
</body>
</html>