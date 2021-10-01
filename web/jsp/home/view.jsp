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
          <div class="slideshow-container">

            <div class="mySlides fade">
              <img src="images/cesta3.jpg" style="width:100%">
            </div>

            <div class="mySlides fade">
              <img src="images/cesta4.jpg" style="width:100%">
            </div>

            <div class="mySlides fade">
              <img src="images/cesta5.jpg" style="width:100%">
            </div>

            <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
            <a class="next" onclick="plusSlides(1)">&#10095;</a>

          </div>
          <br>

          <div style="text-align:center">
            <span class="dot" onclick="currentSlide(1)"></span>
            <span class="dot" onclick="currentSlide(2)"></span>
            <span class="dot" onclick="currentSlide(3)"></span>
          </div>

          <script>
              var slideIndex = 1;
              showSlides(slideIndex);

              function plusSlides(n) {
                  showSlides(slideIndex += n);
              }

              function currentSlide(n) {
                  showSlides(slideIndex = n);
              }

              function showSlides(n) {
                  var i;
                  var slides = document.getElementsByClassName("mySlides");
                  var dots = document.getElementsByClassName("dot");
                  if (n > slides.length) {slideIndex = 1}
                  if (n < 1) {slideIndex = slides.length}
                  for (i = 0; i < slides.length; i++) {
                      slides[i].style.display = "none";
                  }
                  for (i = 0; i < dots.length; i++) {
                      dots[i].className = dots[i].className.replace(" active", "");
                  }
                  slides[slideIndex-1].style.display = "block";
                  dots[slideIndex-1].className += " active";
              }
          </script>
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
