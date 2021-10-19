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
      .Cart-Items{
        margin: auto;
        width: 120%;
        height: 30%;
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
      .counter{
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
      .btn{
        width: 25px;
        height: 25px;
        border-radius: 50%;
        background-color: #CBC7E6;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 20px;
        cursor: pointer;
      }
      .count{
        font-size: 16px;
        margin: 0px 15px 0px 15px;
      }
      .prices{
        height: 100%;
        text-align: right;
      }
      .amount{
        padding-top: 20px;
        font-size: 20px;
      }
      .remove{
        padding-top: 5px;
        font-size: 14px;
        color: #E44C4C;
        cursor: pointer;
      }
      hr{
        width: 66%;
        float: right;
        margin-right: 5%;
      }
      .checkout{
        float: right;
        margin-right: 10%;
      }
      .total{
        width: 100%;
        display: flex;
        justify-content: space-between;
      }
    </style>
  </head>
  <body>
    <%@include file="/include/header.jsp"%>
    <main class="clearfix">
      <div id="site_content">
        <div id="content">
          <%for (i = 0; i<carrelli.size(); i++) {%>
          <div class="Cart-Items">
            <div class="about">
              <h1 style="color: #2E3138"><%=carrelli.get(i).getProduct().getNomeProdotto()%></h1>
            </div>
            <div class="counter">
                <div class="btn">+</div>
                <div class="count"><%=carrelli.get(i).getQuantita()%></div>
                <div class="btn">-</div>
              </div>
            <div class="prices">
              <div class="amount"><%=carrelli.get(i).getProduct().getPrezzoTot().multiply(BigDecimal.valueOf(carrelli.get(i).getQuantita()))%> &euro;</div>
              <div class="remove"><u>Remove</u></div>
              <% total = total.add(carrelli.get(i).getProduct().getPrezzoTot().multiply(BigDecimal.valueOf(carrelli.get(i).getQuantita()))); %>
            </div>
          </div>
          <%}%>
        </div>
      </div>
      <div class="sidebar">
        <div class="checkout">
          <div class="total">
            <h1>Totale : <%=total%> &euro;</h1>
          </div>
          <button>Checkout</button>
        </div>
      </div>
    </main>
    <%@include file="/include/footer.jsp"%>
  </body>
</html>