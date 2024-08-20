<#include "layout.ftl">
<main class="container">
    <div class="d-flex justify-content-between align-items-center">
        <h3>Historial de Movimientos</h3>
    </div>

    <div class="table-responsive card p-3">
        <table class="table table-hover" id="productsTable">
            <thead>
            <tr>
                <th>ID Movimiento</th>
                <th>ID Producto</th>
                <th>Nombre Producto</th>
                <th>Tipo Movimiento</th>
                <th>Cantidad</th>
                <th>Fecha</th>
                <th>Usuario</th>
            </tr>
            </thead>
            <tbody id="table">
            <#list movimientos as m>
                <tr>
                    <td>${m.id}</td>
                    <td>${m.producto.id}</td>
                    <td>${m.producto.nombre}</td>
                    <td>${m.cantidad}</td>
                    <td>${m.tipo}</td>
                    <td>${m.fecha}</td>
                    <td>${m.usuario.userName}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</main>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<#--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js" integrity="sha384-kXNVqIvZh9j50UAnbFrjPT9O2iNpt5fNr1DHCvKuJdRjBLdqV5NHb+8+8AQy50mp" crossorigin="anonymous"></script>-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/2.0.8/js/dataTables.js"></script>

<script>
    $(document).ready( function () {
        $('#productsTable').DataTable({
            language: {
                info: "Mostrando _START_-_END_ de _TOTAL_ movimientos en total",
                infoEmpty: 'Lista de movimientos vacía',
                infoFiltered: '(filtered from _MAX_ total records)',
                lengthMenu: 'Display _MENU_ records per page',
                emptyTable: 'La lista está vacía',
                search: "Buscar:"
            },
            ordering:  false,
            paging: false
        });
    });

</script>