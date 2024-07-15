<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Productos</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Manrope:wght@200&display=swap');
        body {
            font-family: 'Manrope', sans-serif;
            background:#eee;
        }
        .size span {
            font-size: 11px;
        }
        .color span {
            font-size: 11px;
        }
    </style>
</head>
<body>
<#include "navbar.ftl">
<h1>Productos</h1>

<div class="input-group flex-nowrap">
    <span class="input-group-text">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
        </svg>
    </span>
    <input type="text" class="form-control" id="searchInput" placeholder="Escribe para buscar..." onkeyup="filterTable()">
</div>

<div class="container-center">
    <button class="btn-md btn-primary" data-toggle="modal" data-target="#createProductModal">Crear producto</button>
</div>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th>Nombre</th>
        <th>Precio</th>
        <th>Cantidad</th>
        <th>Descripción</th>
        <th>Categoría</th>
        <th>Cantidad Mínima</th>
        <th>Acción</th>
    </tr>
    </thead>
    <tbody id="table">
    <#list productos as p>
        <tr>
            <td>${p.nombre}</td>
            <td>${p.precio}</td>
            <td>${p.cantidad}</td>
            <td>${p.descripcion}</td>
            <td>${p.categoria}</td>
            <td>${p.cantidadMinima}</td>
            <td>
                <button class="btn-sm btn-primary" data-toggle="modal" data-target="#modifyProductModal-${p.id}">Modificar</button>
                <button class="btn-sm btn-danger" data-toggle="modal" data-target="#confirmDeleteModal-${p.id}">Eliminar</button>
            </td>
        </tr>
    </tbody>
</table>

<!-- Modal para modificar producto -->
<div class="modal fade" id="modifyProductModal-${p.id}" tabindex="-1" role="dialog" aria-labelledby="modifyProductModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modifyProductModalLabel">Modificar Producto</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="modifyProductForm" method="post" action="modificar-prod/${p.id}">
                    <div class="form-group">
                        <label for="modifyNombre">Nombre</label>
                        <input type="text" class="form-control" name="nombre" id="modifyNombre" value="${p.nombre}" required>
                    </div>
                    <div class="form-group">
                        <label for="modifyPrecio">Precio</label>
                        <input type="number" class="form-control" name="precio" id="modifyPrecio" value="${p.precio}" required>
                    </div>
                    <div class="form-group">
                        <label for="modifyCantidad">Cantidad</label>
                        <input type="number" class="form-control" name="cantidad" id="modifyCantidad" value="${p.cantidad}" required>
                    </div>
                    <div class="form-group">
                        <label for="modifyDescripcion">Descripción</label>
                        <textarea class="form-control" name="descripcion" id="modifyDescripcion">${p.descripcion}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="modifyCategoria">Categoría</label>
                        <input type="text" class="form-control" name="categoria" id="modifyCategoria" value=${p.categoria} required>
                    </div>
                    <div class="form-group">
                        <label for="modifyCantidadMinima">Cantidad Mínima</label>
                        <input type="number" class="form-control" name="cantidadMinima" id="modifyCantidadMinima" value=${p.cantidadMinima} required>
                    </div>
                    <button type="submit" class="btn btn-primary">Modificar</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal para confirmar eliminación -->
    <div class="modal fade" id="confirmDeleteModal-${p.id}" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmDeleteModalLabel">Confirmar Eliminación</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    ¿Estás seguro de que deseas eliminar este registro?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                   <a href="eliminar-prod/${p.id}"><button type="button" class="btn btn-danger" id="confirmDeleteButton">Eliminar</button></a>
                </div>
            </div>
        </div>
    </div>
</#list>

<!-- Modal para crear producto -->
<div class="modal fade" id="createProductModal" tabindex="-1" role="dialog" aria-labelledby="createProductModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createProductModalLabel">Crear Producto</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="createProductForm" method="post" action="/crear-prod">
                    <div class="form-group">
                        <label for="nombre">Nombre</label>
                        <input type="text" class="form-control" name="nombre" id="nombre" required>
                    </div>
                    <div class="form-group">
                        <label for="precio">Precio</label>
                        <input type="number" class="form-control" name="precio" id="precio" required>
                    </div>
                    <div class="form-group">
                        <label for="cantidad">Cantidad</label>
                        <input type="number" class="form-control" name="cantidad" id="cantidad" required>
                    </div>
                    <div class="form-group">
                        <label for="descripcion">Descripción</label>
                        <textarea class="form-control" name="descripcion" id="descripcion"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="categoria">Categoría</label>
                        <input type="text" class="form-control" name="categoria" id="categoria" required>
                    </div>
                    <div class="form-group">
                        <label for="cantidadMinima">Cantidad Mínima</label>
                        <input type="number" class="form-control" name="cantidadMinima" id="cantidadMinima" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Crear</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script>
    function filterTable() {
        let input, filter, table, tr, td, i, j, visible;
        input = document.getElementById("searchInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("table");
        tr = table.getElementsByTagName("tr");

        for (i = 0; i < tr.length; i++) {
            visible = false;
            if (tr[i].getElementsByTagName("th").length == 0) {
                td = tr[i].getElementsByTagName("td");
                for (j = 0; j < td.length; j++) {
                    if (td[j].innerHTML.toUpperCase().indexOf(filter) > -1) {
                        visible = true;
                    }
                }
            } else {
                visible = true;
            }
            if (visible) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
</script>
</body>
</html>
