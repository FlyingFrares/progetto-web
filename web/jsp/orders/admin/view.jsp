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
  List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
  List<Utente> utenti = (List<Utente>) request.getAttribute("utenti");
  Utente utente = (Utente) request.getAttribute("utente");
%>
<script>
    function selectUser(userID) {
        let id = document.getElementById('selusr');
        id.setAttribute('value', userID);
        document.selectUserForm.submit();
    }

    function modifyOrder(orderID) {
        let id = document.getElementById('modify');
        id.setAttribute('value', orderID);
        document.modifyOrderForm.submit();
    }

    function deleteOrder(orderID) {
        let id = document.getElementById('del');
        id.setAttribute('value',orderID);
        document.deleteOrderForm.submit();
    }
</script>

<!DOCTYPE html>
<html>
<head>
  <%@include file="/include/htmlHead.jsp"%>
  <style>
    table {
      border-collapse: collapse;
      width: 1280px;
    }
    .button {
      text-align: center;
      font-size: 28px;
      padding: 15px;
      transition: all 0.5s;
      cursor: pointer;
      margin: 0px;
    }

    .button span {
      cursor: pointer;
      display: inline-block;
      position: relative;
      transition: 0.5s;
    }

    .button span:after {
      position: absolute;
      opacity: 0;
      top: 0;
      right: -20px;
      transition: 0.5s;
    }

    .button:hover span {
      padding-right: 20px;
    }

    .button:hover span:after {
      opacity: 1;
      right: 0;
    }
  </style>
</head>
<body>
<%@include file="/include/header.jsp"%>
<main class="clearfix">
  <div id="site_content">
    <div id="content">
      <h1>Ordini di <%=utente.getUsername()%></h1>
      <table>
        <tr>
          <th>ID Ordine</th>
          <th>Data</th>
          <th>Destinatario</th>
          <th>Indirizzo di spedizione</th>
          <th>Totale</th>
          <th>Numero carta</th>
          <th>Stato</th>
          <th>Cancella</th>
          <th>Modifica</th>
        </tr>
        <%for (i = 0; i<ordini.size(); i++) {%>
        <tr>
          <td><%=ordini.get(i).getOrderID()%></td>
          <td><%=ordini.get(i).getData()%></td>
          <td><%=ordini.get(i).getDestinatario()%></td>
          <td><%=ordini.get(i).getIndirizzo()%></td>
          <td><%=ordini.get(i).getTotale()%> &euro;</td>
          <td><%=ordini.get(i).getIDpagamento()%></td>
          <td><%=ordini.get(i).getStato()%></td>
          <td>
            <button class="button" onclick="deleteOrder(<%=ordini.get(i).getOrderID()%>)"><span></span></button>
          </td>
          <td>
            <button class="button" onclick="modifyOrder(<%=ordini.get(i).getOrderID()%>)"><span></span></button>
          </td>
        </tr>
        <%}%>
      </table>
      <div class="container">
        <select name="utente" onchange="selectUser(this.value)">
          <option hidden>Cambia utente</option>
          <%for (i = 0; i<utenti.size(); i++) {%>
          <option value="<%=utenti.get(i).getUserID()%>"><%=utenti.get(i).getUsername()%></option>
          <%}%>
        </select><br>
      </div>
    </div>
  </div>
</main>

<form name="deleteOrderForm" action="Dispatcher" method="post">
  <input type="hidden" id="del" name="orderID"/>
  <input type="hidden" name="controllerAction" value="Orders.deleteOrder"/>
</form>

<form name="modifyOrderForm" action="Dispatcher" method="post">
  <input type="hidden" id="modify" name="orderID"/>
  <input type="hidden" name="controllerAction" value="Orders.modifyOrderView"/>
</form>

<form name="selectUserForm" action="Dispatcher" method="post">
  <input type="hidden" id="selusr" name="userID"/>
  <input type="hidden" name="controllerAction" value="Orders.adminView"/>
</form>

<%@include file="/include/footer.jsp"%>
</body>
</html>
