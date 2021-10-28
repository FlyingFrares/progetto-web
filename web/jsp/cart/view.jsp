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

<script>
    function remove(cartID) {
        let id = document.getElementById("remove");
        id.setAttribute('value',cartID);
        document.removeForm.submit();
    }
</script>

<!DOCTYPE html>
<html>
  <head>
   <%@include file="/include/htmlHead.jsp"%>
    <style>
      .Cart-Items{
        margin: auto;
        width: 120%;
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
      form {
        display: flex;
      }
      .counter{
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
      .prices{
        height: 100%;
        text-align: right;
      }
      .amount{
        font-size: 20px;
        font-family: monospace;
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
              <input type="submit" style="color: red; margin-right: 5px" onclick="remove(<%=carrelli.get(i).getCartID()%>)"  value="Rimuovi">
              <form name="updateForm" action="Dispatcher" method="post">
                <input type="number" style="width: 40px" name="qty" value="<%=carrelli.get(i).getQuantita()%>" min="1" step="1" max="<%=carrelli.get(i).getProduct().getMagazzino()%>" required>
                <input type="hidden" name="cartID" value="<%=carrelli.get(i).getCartID()%>">
                <input type="hidden" name="controllerAction" value="Cart.modify"/>
                <input type="submit" style="margin-left: 5px" value="Aggiorna">
              </form>
            </div>
            <div class="prices">
              <div class="amount"><%=carrelli.get(i).getProduct().getPrezzoTot().multiply(BigDecimal.valueOf(carrelli.get(i).getQuantita()))%> &euro;</div>
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
          <button onclick="location.href='Dispatcher?controllerAction=Cart.checkoutView'">Checkout</button>
        </div>
      </div>

      <form name="removeForm" action="Dispatcher" method="post">
        <input type="hidden" id="remove" name="cartID"/>
        <input type="hidden" name="controllerAction" value="Cart.removeItem"/>
      </form>

    </main>
    <%@include file="/include/footer.jsp"%>
  </body>
</html>