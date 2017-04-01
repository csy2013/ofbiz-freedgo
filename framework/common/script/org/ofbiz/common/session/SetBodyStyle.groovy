t = request.getParameter("t");
println "true ====================== $t"
if(t.equals("")|| t.equals("default")){
    session.removeAttribute("bodystyle");
}else {
    session.setAttribute("bodystyle",t);
}

return "success"