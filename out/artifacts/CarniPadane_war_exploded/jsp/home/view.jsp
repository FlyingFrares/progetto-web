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
       <%@include file="/include/sidebar.jsp"%>
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
                    <p>Harvest Vases are a reinterpretation<br> of peeled fruits and vegetables as<br> functional objects. The surfaces<br> appear to be sliced and pulled aside,<br> allowing room for growth. </p>
                  </div>
                  <div class="product-price-btn">
                    <p><span>78</span>$</p>
                    <button type="button">buy now</button>
                  </div>
                </div>
              </div>
            </div>

            <div class="mySlides fade">
              <div class="wrapper">
                <div class="product-img">
                  <img src="images/gentile.jpg" height="420" width="327">
                </div>
                <div class="product-info">
                  <div class="product-text">
                    <h1>Harvest Vase</h1>
                    <h2>by studio and friends</h2>
                    <p>Harvest Vases are a reinterpretation<br> of peeled fruits and vegetables as<br> functional objects. The surfaces<br> appear to be sliced and pulled aside,<br> allowing room for growth. </p>
                  </div>
                  <div class="product-price-btn">
                    <p><span>78</span>$</p>
                    <button type="button">buy now</button>
                  </div>
                </div>
              </div>
            </div>

            <div class="mySlides fade">
              <div class="wrapper">
                <div class="product-img">
                  <img src="images/dolce_goccia.jpg" height="420" width="327">
                </div>
                <div class="product-info">
                  <div class="product-text">
                    <h1>Harvest Vase</h1>
                    <h2>by studio and friends</h2>
                    <p>Harvest Vases are a reinterpretation<br> of peeled fruits and vegetables as<br> functional objects. The surfaces<br> appear to be sliced and pulled aside,<br> allowing room for growth. </p>
                  </div>
                  <div class="product-price-btn">
                    <p><span>78</span>$</p>
                    <button type="button">buy now</button>
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
    </main>
    <%@include file="/include/footer.jsp"%>
  </body>
</html>
