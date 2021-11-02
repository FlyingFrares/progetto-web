<%@page session="false"%>
<%@page import="model.mo.Utente"%>
<%@ page import="java.util.List" %>
<%@ page import="model.mo.Prodotto" %>

<%
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");   /* Stringa passata dal Controller */
  String menuActiveLink = "Home";
%>

<script>
    function requestLogin() {
        alert("Devi effettuare il Login");
    }

    function addToCart(productID) {
        let id = document.getElementById('atc');
        id.setAttribute('value',productID);
        document.addToCartForm.submit();
    }
</script>

<!DOCTYPE HTML>
<html>
  <head>
    <%@include file="/include/htmlHead.jsp"%>
    <style>
      .product-info {
        float: left;
        height: 420px;
        width: 327px;
        border-radius: 0 7px 10px 7px;
        background-color: #ffffff;
      }
    </style>
  </head>
  <body>
    <%@include file="/include/header.jsp"%>
    <main class="clearfix">
      <div id="site_content">
        <div class="sidebar">
          <%@include file="/include/sidebar.jsp"%>
        </div>
        <div id="content">
          <!-- insert the page content here -->
          <div class="slideshow-container">
            <div class="mySlides fade">
              <div class="wrapper">
                <div class="product-img">
                  <img src="images/gran_fico.jpeg" height="420" width="327">
                </div>
                <div class="product-info">
                  <div class="product-text">
                    <h1>Gran fico</h1>
                    <h2>Specialit&agrave</h2>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                      <br>Quisque aliquet nec lacus ut vulputate.
                      <br>In molestie, nulla et placerat porttitor, arcu purus dictum augue, eu fringilla augue dolor nec eros.
                    </p>
                  </div>
                  <div class="product-price-btn">
                    <p>85.05 &euro;</p>
                    <% if(!loggedOn) { %>
                    <button type="button" onclick="requestLogin()">Acquista</button>
                    <% } else { %>
                    <button type="button" onclick='addToCart(31)'>Acquista</button>
                    <% } %>
                  </div>
                </div>
              </div>
            </div>

            <div class="mySlides fade">
              <div class="wrapper">
                <div class="product-img">
                  <img src="images/salame.jpg" height="420" width="327">
                </div>
                <div class="product-info">
                  <div class="product-text">
                    <h1>Il Gentile</h1>
                    <h2>Prodotti nostrani</h2>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                      <br>Quisque aliquet nec lacus ut vulputate.
                      <br>In molestie, nulla et placerat porttitor, arcu purus dictum augue, eu fringilla augue dolor nec eros.
                    </p>
                  </div>
                  <div class="product-price-btn">
                    <p>11.50 &euro;</p>
                    <button type="button" onclick='addToCart(24)'>Acquista</button>
                  </div>
                </div>
              </div>
            </div>

            <div class="mySlides fade">
              <div class="wrapper">
                <div class="product-img">
                  <img src="images/gonzaga.jpg" height="420" width="327">
                </div>
                <div class="product-info">
                  <div class="product-text">
                    <h1>Gonzaga</h1>
                    <h2>Prosciutto crudo</h2>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                      <br>Quisque aliquet nec lacus ut vulputate.
                      <br>In molestie, nulla et placerat porttitor, arcu purus dictum augue, eu fringilla augue dolor nec eros.
                    </p>
                  </div>
                  <div class="product-price-btn">
                    <p>110.00 &euro;</p>
                    <button type="button" onclick='addToCart(8)'>Acquista</button>
                  </div>
                </div>
              </div>
            </div>

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

      <form name="addToCartForm" action="Dispatcher" method="post">
        <input type="hidden" id="atc" name="productID"/>
        <input type="hidden" name="controllerAction" value="Category.addToCart"/>
      </form>

    </main>
    <%@include file="/include/footer.jsp"%>
  </body>
</html>
