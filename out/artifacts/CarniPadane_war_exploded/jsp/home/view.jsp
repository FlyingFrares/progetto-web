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
    <%@include file="/include/htmlHead.inc"%>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
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
          <h3>Latest News</h3>
          <h4>New Website Launched</h4>
          <h5>August 1st, 2013</h5>
          <p>2013 sees the redesign of our website. Take a look around and let us know what you think.<br /><a href="#">Read more</a></p>
          <p></p>
          <h4>New Website Launched</h4>
          <h5>August 1st, 2013</h5>
          <p>2013 sees the redesign of our website. Take a look around and let us know what you think.<br /><a href="#">Read more</a></p>
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
          <h1>Welcome to the textured_blue template</h1>
          <p>This standards compliant, simple, fixed width website template is released as an 'open source' design (under a <a href="http://creativecommons.org/licenses/by/3.0">Creative Commons Attribution 3.0 Licence</a>), which means that you are free to download and use it for anything you want (including modifying and amending it). All I ask is that you leave the 'design from HTML5webtemplates.co.uk' link in the footer of the template, but other than that...</p>
          <p>This template is written entirely in <strong>HTML5</strong> and <strong>CSS</strong>, and can be validated using the links in the footer.</p>
          <p>You can view more free HTML5 web templates <a href="http://www.html5webtemplates.co.uk">here</a>.</p>
          <p>This template is a fully functional 5 page website, with an <a href="examples.html">examples</a> page that gives examples of all the styles available with this design.</p>
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
    <%@include file="/include/footer.inc"%>
  </body>
</html>
