<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Shoes Happy</title>
    
    <!-- Adicione o link para o Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    
    <!-- Adicione o link para o Bootstrap JavaScript e jQuery -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h1>Login - Shoes Happy</h1>
        <form method="post" action="ServletOi" class="mt-3">
            <div class="mb-3">
                <label for="usuario" class="form-label">Usuário</label>
                <input type="text" class="form-control" id="usuario" name="usuario" required>
            </div>
            <div class="mb-3">
                <label for="senha" class="form-label">Senha</label>
                <input type="password" class="form-control" id="senha" name="senha" required>
            </div>
            <input type="hidden" value="<%= request.getParameter("url") %>" name="url">
            <button type="submit" class="btn btn-primary">Login</button>
            <a href="index.jsp" class="btn btn-secondary">Home</a>
        </form>
        <h4 class="mt-3">${mensagem}</h4>
    </div>
</body>
</html>
