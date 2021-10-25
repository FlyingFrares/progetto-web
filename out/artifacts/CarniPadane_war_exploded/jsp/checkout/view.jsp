<%@page session="false"%>
<%@page import="model.mo.Utente"%>
<%@page import="model.mo.Carrello"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.math.BigDecimal" %>

<%
  int i = 0;
  BigDecimal total = new BigDecimal(0);
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");   /* Stringa passata dal Controller */
  String menuActiveLink = "Carrello";
  List<Carrello> carrelli = (List<Carrello>) request.getAttribute("carrelli");
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

      input[type=text] {
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
      }

      .btn:hover {
        background-color:#CDB4DB;
      }

      span.price {
        float: right;
        color: grey;
      }
    </style>
  </head>
  <body>
  <%@include file="/include/header.jsp"%>
  <main class="clearfix">
    <div id="site_content">
      <div class="sidebar">
          <div class="container">
            <h4>Cart
              <span class="price" style="color:black">
            <i class="fa fa-shopping-cart"></i>
            <b>4</b>
          </span>
            </h4>
            <p><a href="#">Product 1</a> <span class="price">$15</span></p>
            <p><a href="#">Product 2</a> <span class="price">$5</span></p>
            <p><a href="#">Product 3</a> <span class="price">$8</span></p>
            <p><a href="#">Product 4</a> <span class="price">$2</span></p>
            <hr>
            <p>Total <span class="price" style="color:black"><b>$30</b></span></p>
        </div>
      </div>
      <div id="content">
        <div class="row">
            <div class="container">
              <form action="/action_page.php">
                <div class="row">

                  <div class="col">
                    <h3>Billing Address</h3>
                    <label for="fname"><i class="fa fa-user"></i> Full Name</label>
                    <input type="text" id="fname" name="firstname" placeholder="John M. Doe">
                    <label for="email"><i class="fa fa-envelope"></i> Email</label>
                    <input type="text" id="email" name="email" placeholder="john@example.com">
                    <label for="adr"><i class="fa fa-address-card-o"></i> Address</label>
                    <input type="text" id="adr" name="address" placeholder="542 W. 15th Street">
                    <label for="city"><i class="fa fa-institution"></i> City</label>
                    <input type="text" id="city" name="city" placeholder="New York">
                    <label for="state">State</label>
                    <input type="text" id="state" name="state" placeholder="NY">
                    <label for="zip">Zip</label>
                    <input type="text" id="zip" name="zip" placeholder="10001">
                  </div>

                  <div class="col">
                    <h3>Payment</h3>
                    <label for="cname">Name on Card</label>
                    <input type="text" id="cname" name="cardname" placeholder="John More Doe">
                    <label for="ccnum">Credit card number</label>
                    <input type="text" id="ccnum" name="cardnumber" placeholder="1111-2222-3333-4444">
                    <label for="expmonth">Exp Month</label>
                    <input type="text" id="expmonth" name="expmonth" placeholder="September">
                    <label for="expyear">Exp Year</label>
                    <input type="text" id="expyear" name="expyear" placeholder="2018">
                    <label for="cvv">CVV</label>
                    <input type="text" id="cvv" name="cvv" placeholder="352">
                  </div>
                </div>
               <input type="submit" value="Continue to checkout" class="btn">
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
  <%@include file="/include/footer.jsp"%>
  </body>
</html>