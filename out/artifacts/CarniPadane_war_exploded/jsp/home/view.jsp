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
    <main class="clearfix">
      <div id="site_content">
       <%@include file="/include/sidebar.jsp"%>
        <div id="content">
          <!-- insert the page content here -->
          <div class="slideshow-container">

            <div class="mySlides fade">
              <img src="images/insaccati.jpg" style="width:1080px; height:600px">
            </div>

            <div class="mySlides fade">
              <img src="images/mortadella_bologna.jpg" style="width:1080px; height:600px">
            </div>

            <div class="mySlides fade">
              <img src="images/salame_dritto.jpeg" style="width:1080px; height:600px">
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
        </div>
      </div>
    </main>
    <%@include file="/include/footer.jsp"%>
  </body>
</html>
