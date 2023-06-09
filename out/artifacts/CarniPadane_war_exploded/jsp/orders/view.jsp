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
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.jsp"%>
    <style>
      table {
        border-collapse: collapse;
        width: -webkit-fill-available;
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
        <table>
          <tr>
            <th>ID Ordine</th>
            <th>Data</th>
            <th>Destinatario</th>
            <th>Indirizzo di spedizione</th>
            <th>Totale</th>
            <th>Numero carta</th>
            <th>Stato</th>
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
          </tr>
          <%}%>
        </table>
      </div>
    </main>
    <%@include file="/include/footer.jsp"%>
  </body>
</html>
