<%@page session="false"%>
<%@page import="model.mo.Utente"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>


<%
  int i = 0;
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");   /* Stringa passata dal Controller */
  String menuActiveLink = "Admin";
  List<Utente> utenti = (List<Utente>) request.getAttribute("utenti");
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.jsp"%>
    <style>
      .svg {
        float: right;
      }
      h2 {
        color: #2E3138;
        margin: 0;
        padding: 0;
      }
    </style>
  </head>
  <body>
  <%@include file="/include/header.jsp"%>
    <main class="clearfix">
      <div id="site_content">
        <div class="sidebar">
          <h3>Cerca un utente</h3>
          <form name="searchForm" action="Dispatcher" method="post">
            <input class="search" type="text" id="search" name="search" placeholder="Ricerca" maxlength="40">
            <input type="hidden" name="controllerAction" value="Admin.search"/>
          </form>
          <h3>Aggiungi utente</h3>
          <button type="button" onclick="createAccount()">Crea account</button>
        </div>
        <div id="content">
          <%if (utenti.size() == 0) {%>
          <img src="images/404.png" width="800px">
          <%}for (i = 0; i<utenti.size(); i++) {%>
          <article>
            <h2><%=utenti.get(i).getUsername()%></h2>
            <%if (utenti.get(i).getAdmin().equals("S")) {%>
            <h4 style="color: #99CEFF">Admin</h4>
            <%}else{%>
            <h4 style="color: #FFC2D6">Utente</h4>
            <%}%>

            <span><%=utenti.get(i).getNome()%> <%=utenti.get(i).getCognome()%></span><br>
            <span><%=utenti.get(i).getEmail()%></span>
            <div class="svg">
              <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-edit" width="30" height="30" viewBox="0 0 24 24" stroke-width="1.5" stroke="#2c3e50" fill="none" stroke-linecap="round" stroke-linejoin="round">
                <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                <path d="M9 7h-3a2 2 0 0 0 -2 2v9a2 2 0 0 0 2 2h9a2 2 0 0 0 2 -2v-3" />
                <path d="M9 15h3l8.5 -8.5a1.5 1.5 0 0 0 -3 -3l-8.5 8.5v3" />
                <line x1="16" y1="5" x2="19" y2="8" />
              </svg>
            </div>
          </article>
          <%}%>
        </div>
      </div>
    </main>
  </body>
</html>