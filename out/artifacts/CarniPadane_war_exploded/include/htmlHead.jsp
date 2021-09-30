<meta charset="utf-8"/>

<!-- Linking styles -->
<link rel="stylesheet" href="css/style.css" title="style" type="text/css" media="screen">
<title>Carni Padane: <%=menuActiveLink%></title> <!-- menuActiveLink su ogni JSP -->
<script>
    var applicationMessage;
    <%if (applicationMessage != null) {%>
        applicationMessage="<%=applicationMessage%>"; /* applicationMessage su ogni JSP */
    <%}%>
    function onLoadHandler() {
        headerOnLoadHandler(); /* Metodo implementato in header.jsp */
        try { mainOnLoadHandler(); } catch (e) {}
        if (applicationMessage!=undefined) alert(applicationMessage); /* popup al caricamento della pagina */
    }
    window.addEventListener("load", onLoadHandler);
</script>