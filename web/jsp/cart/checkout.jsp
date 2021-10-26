<%@page session="false"%>
<%@page import="model.mo.Utente"%>
<%@page import="model.mo.Carrello"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="javax.ejb.Local" %>

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

      input[type=text], input[type=month] {
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
      <div class="sidebar" style="width: 300px">
          <div class="container">
            <h4>Carrello</h4>
            <%for (i = 0; i<carrelli.size(); i++) {%>
              <p><%=carrelli.get(i).getQuantita()%> x <%=carrelli.get(i).getProduct().getNomeProdotto()%>
                <span class="price"><%=carrelli.get(i).getProduct().getPrezzoTot().multiply(BigDecimal.valueOf(carrelli.get(i).getQuantita()))%> &euro;</span>
                <% total = total.add(carrelli.get(i).getProduct().getPrezzoTot().multiply(BigDecimal.valueOf(carrelli.get(i).getQuantita()))); %>
              </p>
            <%}%>
            <hr>
            <p>Totale <span class="price" style="color:black"><b><%=total%> &euro;</b></span></p>
        </div>
      </div>
      <div id="content">
        <div class="row">
            <div class="container">
              <form name="checkoutForm" action="Dispatcher" method="post">
                <div class="row">

                  <div class="col">
                    <h3>Spedizione</h3>
                    <label for="name">Nome completo</label>
                    <input type="text" id="name" name="name" placeholder="Mario Rossi" required>
                    <label for="adr">Indirizzo</label>
                    <input type="text" id="adr" name="address" placeholder="Via delle Volte, 1" required>
                    <label for="city">Citt&agrave</label>
                    <input type="text" id="city" name="city" placeholder="Ferrara" required>
                    <label for="cap">CAP</label>
                    <input type="text" id="cap" name="cap" placeholder="44121" required>
                  </div>

                  <div class="col">
                    <h3>Pagamento</h3>
                    <label for="cname">Intestatario carta</label>
                    <input type="text" id="cname" name="cardname" placeholder="Mario Rossi" required>
                    <label for="ccnum">Numero carta</label>
                    <input type="text" id="ccnum" name="cardnumber" placeholder="1111-2222-3333-4444" required>
                    <label for="exp">Data di scadenza</label>
                    <input type="month" id="exp" name="exp" min="2021-11" required>
                    <label for="cvv">CVV</label>
                    <input type="text" id="cvv" name="cvv" placeholder="000" required>
                  </div>

                </div>
                <input type="hidden" name="total" value="<%=total%>">
                <input type="hidden" name="controllerAction" value="Cart.confirm">
               <input type="submit" value="conferma" class="btn">
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