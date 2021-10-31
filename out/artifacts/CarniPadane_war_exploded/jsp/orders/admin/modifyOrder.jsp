<%@page session="false"%>
<%@page import="model.mo.Utente"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="model.mo.Ordine" %>

<%
  int i = 0;
  BigDecimal total = new BigDecimal(0);
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");   /* Stringa passata dal Controller */
  String menuActiveLink = "Orders";
  Ordine ordine = (Ordine) request.getAttribute("ordine");
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

    input[type=text], select {
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

    .delbtn {
      background-color: #99CEFF;
      color: black;
      padding: 12px;
      margin: 10px 0;
      border: none;
      width: 100%;
      border-radius: 3px;
      cursor: pointer;
      font-size: 17px;
    }

    .delbtn:hover {
      background-color:#CDB4DB;
    }
  </style>
</head>
<body>
<%@include file="/include/header.jsp"%>
<main class="clearfix">
  <div id="site_content">
    <div id="content">
      <div class="row">
        <div class="container">
          <form name="checkoutForm" action="Dispatcher" method="post">
            <div class="row">

              <div class="col">
                <h3>Spedizione</h3>
                <label for="name">Destinatario</label>
                <input type="text" id="name" name="name" placeholder="<%=ordine.getDestinatario()%>">
                <label for="adr">Indirizzo</label>
                <input type="text" id="adr" name="address" placeholder="<%=ordine.getIndirizzo()%>">
              </div>

              <div class="col">
                <h3>Stato ordine</h3>
                <select name="stato">
                  <option value="<%=ordine.getStato()%>" selected disabled hidden><%=ordine.getStato()%></option>
                  <option value="Evaso">Evaso</option>
                  <option value="Pagato">Pagato</option>
                  <option value="Spedito">Spedito</option>
                  <option value="Consegnato">Consegnato</option>
                </select>
              </div>

            </div>
            <input type="hidden" name="orderID" value="<%=ordine.getOrderID()%>">
            <input type="hidden" name="userID" value="<%=ordine.getUser().getUserID()%>">
            <input type="hidden" name="controllerAction" value="Orders.modifyOrder">
            <input type="submit" value="conferma" class="btn">
          </form>
          <form name="discardForm" action="Dispatcher" method="post">
            <input type="hidden" name="controllerAction" value="Orders.adminView">
            <input type="submit" value="annulla" class="delbtn">
          </form>
        </div>
      </div>
    </div>
  </div>
</main>
<%@include file="/include/footer.jsp"%>
</body>
</html>