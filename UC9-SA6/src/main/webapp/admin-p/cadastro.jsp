<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="bootstrap.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Painel Administrativo - Cadastro de Usuário</title>
</head>
<body>
    <div class="container mt-5">
        <h3>Painel Administrativo - Cadastro de Usuário</h3>
        <jsp:include page="navbar.jsp"></jsp:include>
    </div>
    <div class="container mt-3">
        <form action="<%= request.getContextPath() %>/ServletUsuario" method="post" id="formUsuario">
            <div class="mb-3">
                <label for="id" class="form-label">Matrícula</label>
                <input type="text" class="form-control" id="id" name="id" placeholder="Selecione um Usuário para a sua Matrícula ser Gerada" readonly="readonly" value="${user01.id}">
            </div>
            <div class="mb-3">
                <label for="usuario" class="form-label">Usuário</label>
                <input type="text" class="form-control" id="usuario" name="usuario" placeholder="Nome do Usuário" value="${user01.usuario}">
            </div>
            <div class="mb-3">
                <label for="senha" class="form-label">Senha</label>
                <input type="password" class="form-control" id="senha" name="senha" placeholder="Senha" value="${user01.senha}">
            </div>
            <input type="hidden" name="acao" id="acao" value="">
            <button type="submit" class="btn btn-success">Salvar</button>
            <button type="button" class="btn btn-info" onclick="limparDados();">Novo</button>
            <button type="button" class="btn btn-warning" onclick="apagarUsuario();">Apagar</button>
            <button type="button" class="btn btn-danger" onclick="apagarUsuarioAjax();">Apagar Ajax</button>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#meuModal">Consultar Usuário</button>
        </form>
    </div>
    <div class="container mt-3">
        <span id="mensagem">${mensagem}</span>
    </div>

    <!-- Modal de Consulta de Usuário -->
    <div class="modal" id="meuModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">Consultar Usuário</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <!-- Modal body -->
                <div class="modal-body">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="Nome" aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
                        <div class="input-group-append">
                            <button class="btn btn-success" type="button" onclick="consultarUsuario();">Consultar</button>
                        </div>
                    </div>
                    <table class="table" id="tableUsuario">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Usuário</th>
                                <th scope="col">Ação</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <span id="totalResultados"></span>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        function editarUsuario(id) {
            var urlAction = document.getElementById("formUsuario").action;
            window.location.href = urlAction + '?acao=buscaEditar&id=' + id;
        }

        function consultarUsuario() {
            var nomeBusca = document.getElementById('nomeBusca').value;

            if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '') {
                var urlAction = document.getElementById("formUsuario").action;

                $.ajax({
                    method: "get",
                    url: urlAction,
                    data: "nomeBusca=" + nomeBusca + '&acao=consultarAjax',
                    success: function (response) {

                        var json = JSON.parse(response);
                        $('#tableUsuario > tbody > tr').remove();
                        for (var x = 0; x < json.length; x++) {
                            $('#tableUsuario > tbody').append('<tr> <td>' + json[x].id + '</td><td>' + json[x].usuario + '</td><td><button onclick="editarUsuario(' + json[x].id + ')" type="button" class="btn btn-info">Editar</button></tr>')
                        }
                        document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
                    }

                }).fail(function (xhr, status, errorThrown) {
                    alert('Erro ao consultar usuário com Ajax: ' + xhr.responseText);
                });
            }

        }

        function apagarUsuarioAjax() {
            if (confirm("Deseja realmente apagar o Usuário com Ajax")) {
                var urlAction = document.getElementById("formUsuario").action;
                var idUser = document.getElementById('id').value;

                $.ajax({
                    method: "get",
                    url: urlAction,
                    data: "id=" + idUser + '&acao=deletarAjax',
                    success: function (response) {
                        limparDados();
                        document.getElementById('mensagem').textContent = response;
                    }

                }).fail(function (xhr, status, errorThrown) {
                    alert('Erro ao deletar usuário com Ajax: ' + xhr.responseText);
                });
            }
        }

        function apagarUsuario() {
            if (confirm("Deseja realmente apagar o Usuário")) {
                document.getElementById("formUsuario").method = 'get';
                document.getElementById("acao").value = 'deletar';
                document.getElementById("formUsuario").submit();
            }
        }

        function limparDados() {
            var campos = document.getElementById("formUsuario").elements;

            for (var i = 0; i < campos.length; i++) {
                campos[i].value = '';
            }
        }
    </script>
</body>
</html>
