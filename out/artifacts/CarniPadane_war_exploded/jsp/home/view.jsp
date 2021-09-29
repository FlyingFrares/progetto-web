<%@page session="false"%>
<%@page import="model.mo.Utente"%>

<%
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");   /* Stringa passata dal Controller */
  String menuActiveLink = "Home";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <%@include file="/include/htmlHead.jsp"%>
  </head>
  <body>
    <%@include file="/include/header.jsp"%>
    <main>
      <div id="site_content">
        <div class="sidebar">
          <!-- insert your sidebar items here -->
          <h3>Cerca un prodotto</h3>
          <form name="searchForm" action="Dispatcher" method="post">
            <input class="search" type="text" name="search" placeholder="Ricerca" maxlength="40" required>
            <input type="hidden" name="controllerAction" value="HomeManagement.search"/>
            <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-search" width="40" height="40" viewBox="0 0 24 24" stroke-width="1.5" stroke="#2E3138" fill="none" stroke-linecap="round" stroke-linejoin="round">
              <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
              <circle cx="10" cy="10" r="7" />
              <line x1="21" y1="21" x2="15" y2="15" />
            </svg>
          </form>
          <h3>Ultime notizie</h3>
          <h4>New Website Launched</h4>
          <h5>August 1st, 2013</h5>
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
          <h3>Useful Links</h3>
          <ul>
            <li><a href="#">link 1</a></li>
            <li><a href="#">link 2</a></li>
            <li><a href="#">link 3</a></li>
            <li><a href="#">link 4</a></li>
          </ul>
        </div>
        <div id="content">
          <!-- insert the page content here -->

          <div class="grid-container">
            <div class="grid-item">1</div>
            <div class="grid-item">2</div>
            <div class="grid-item">3</div>
            <div class="grid-item">4</div>
            <div class="grid-item">5</div>
            <div class="grid-item">6</div>
            <div class="grid-item">7</div>
            <div class="grid-item">8</div>
            <div class="grid-item">9</div>
          </div>
          <h2>Browser Compatibility</h2>
          <p>This template has been tested in the following browsers:</p>
          <ul>
            <li>Internet Explorer 8</li>
            <li>Internet Explorer 7</li>
            <li>FireFox 3.5</li>
            <li>Google Chrome 6</li>
          </ul>
        </div>
      </div>
    </main>
    <%@include file="/include/footer.jsp"%>
  </body>
</html>
