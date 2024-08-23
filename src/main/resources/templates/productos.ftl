<#include "layout.ftl">
<main class="container">
    <div class="d-flex justify-content-between align-items-center">
        <h3>Productos</h3>
        <button class="btn btn-primary m-3" data-bs-toggle="modal" data-bs-target="#createProductModal">Crear producto</button>
    </div>

    <div class="table-responsive card p-3">
        <table class="table table-hover" id="productsTable">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Descripción</th>
                    <th>Categoría</th>
                    <th>Cantidad Mínima</th>
                    <th></th>
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
                        <button class="btn px-1 py-0" data-bs-toggle="modal" data-bs-target="#stockupProductModal-${p.id}">
                            <span class="material-symbols-outlined text-primary">add</span>
                        </button>
                        <button class="btn px-1 py-0" data-bs-toggle="modal" data-bs-target="#stockdownProductModal-${p.id}">
                            <span class="material-symbols-outlined text-primary">remove</span>
                        </button>
                        <button class="btn px-1 py-0" data-bs-toggle="modal" data-bs-target="#modifyProductModal-${p.id}">
                            <span class="material-symbols-outlined text-primary">edit</span>
                        </button>
                        <button class="btn px-1 py-0" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal-${p.id}">
                            <span class="material-symbols-outlined text-danger">delete</span>
                        </button>
                    </td>
                </tr>

                <!-- Modal para incrementar stock producto -->
                <div class="modal fade" id="stockupProductModal-${p.id}" tabindex="-1" role="dialog" aria-labelledby="stockupProductModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="stockupProductModalLabel">Agregar stock</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form class="needs-validation" id="stockProductForm-${p.id}" method="post" action="/incrementar-stock/${p.id}" novalidate>
                                    <div class="mb-3">
                                        <label for="cantidadIncrementar${p.id}" class="form-label">cantidad</label>
                                        <input type="number" class="form-control" name="cantidadIncrementar" id="cantidadIncrementar${p.id}" value="1" min="1" required>
                                        <div class="invalid-feedback">
                                            Por favor, ingrese cantidad.
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary my-3" id="submit">Guardar</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Modal para decrementar stock producto -->
                <div class="modal fade" id="stockdownProductModal-${p.id}" tabindex="-1" role="dialog" aria-labelledby="stockdownProductModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="stockdownProductModalLabel">Decrementar stock</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form class="needs-validation" id="stockdownProductForm-${p.id}" method="post" action="/decrementar-stock/${p.id}" novalidate>
                                    <div class="mb-3">
                                        <label for="cantidadDecrementar${p.id}" class="form-label">cantidad</label>
                                        <input type="number" class="form-control" name="cantidadDecrementar" id="cantidadDecrementar${p.id}" value="1" min="1" required>
                                        <div class="invalid-feedback">
                                            Por favor, ingrese cantidad.
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary my-3" id="submit">Guardar</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Modal para modificar producto -->
                <div class="modal fade" id="modifyProductModal-${p.id}" tabindex="-1" role="dialog" aria-labelledby="modifyProductModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="modifyProductModalLabel">Modificar Producto</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form class="needs-validation" id="modifyProductForm-${p.id}" method="post" action="modificar-prod/${p.id}" novalidate>
                                    <div class="mb-3">
                                        <label for="modifyNombre${p.id}" class="form-label">Nombre</label>
                                        <input type="text" class="form-control" name="nombre" id="modifyNombre${p.id}" value="${p.nombre}" required>
                                        <div class="invalid-feedback">
                                            Por favor, ingrese un nombre.
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="modifyPrecio${p.id}" class="form-label">Precio</label>
                                        <input type="number" class="form-control" name="precio" id="modifyPrecio${p.id}" value="${p.precio}" required min="0.01" step="0.01">
                                        <div class="invalid-feedback">
                                            Por favor, ingrese un precio válido.
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="modifyCantidad${p.id}" class="form-label">Cantidad</label>
                                        <input type="number" class="form-control" name="cantidad" id="modifyCantidad${p.id}" value="${p.cantidad}" required min="0.00">
                                        <div class="invalid-feedback">
                                            Por favor, ingrese una cantidad válida.
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="modifyDescripcion${p.id}" class="form-label">Descripción</label>
                                        <textarea class="form-control" name="descripcion" id="modifyDescripcion${p.id}" required>${p.descripcion}</textarea>
                                        <div class="invalid-feedback">
                                            Por favor, ingrese una descripción.
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="modifyCategoria${p.id}" class="form-label">Categoría</label>
                                        <input type="text" class="form-control" name="categoria" id="modifyCategoria${p.id}" value="${p.categoria}" required>
                                        <div class="invalid-feedback">
                                            Por favor, ingrese una categoría.
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="modifyCantidadMinima${p.id}" class="form-label">Cantidad Mínima</label>
                                        <input type="number" class="form-control" name="cantidadMinima" id="modifyCantidadMinima${p.id}" value="${p.cantidadMinima}" required>
                                        <div class="invalid-feedback">
                                            Por favor, ingrese una cantidad mínima válida.
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary my-3" id="submit${p.id}">Modificar</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Modal para confirmar eliminación -->
                <div class="modal fade" id="confirmDeleteModal-${p.id}" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="confirmDeleteModalLabel">Confirmar Eliminación</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
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
            </tbody>
        </table>
    </div>

    <!-- Modal para crear producto -->
    <div class="modal fade" id="createProductModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Crear Producto</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="createProductForm" class="needs-validation" method="post" action="/crear-prod" novalidate>
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre</label>
                            <input type="text" class="form-control" name="nombre" id="nombre" required>
                            <div class="invalid-feedback">
                                Por favor, ingrese un nombre.
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="precio" class="form-label">Precio</label>
                            <input type="number" class="form-control" name="precio" id="precio" required min="0.01" step="0.01">
                            <div class="invalid-feedback">
                                Por favor, ingrese un precio válido.
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="cantidad" class="form-label">Cantidad</label>
                            <input type="number" class="form-control" name="cantidad" id="cantidad" required min="0.00">
                            <div class="invalid-feedback">
                                Por favor, ingrese una cantidad válida.
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="descripcion" class="form-label">Descripción</label>
                            <textarea class="form-control" name="descripcion" id="descripcion" rows="3" required></textarea>
                            <div class="invalid-feedback">
                                Por favor, ingrese una descripción.
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="categoria" class="form-label">Categoría</label>
                            <input type="text" class="form-control" name="categoria" id="categoria" required>
                            <div class="invalid-feedback">
                                Por favor, ingrese una categoría.
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="cantidadMinima" class="form-label">Cantidad Mínima</label>
                            <input type="number" class="form-control" name="cantidadMinima" id="cantidadMinima" required min="0">
                            <div class="invalid-feedback">
                                Por favor, ingrese una cantidad mínima válida.
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary my-3">Crear</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</main>
<script>
    // JavaScript for Bootstrap 5 validation
    (function () {
        'use strict'

        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.querySelectorAll('.needs-validation')

        // Loop over them and prevent submission
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }

                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<#--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js" integrity="sha384-kXNVqIvZh9j50UAnbFrjPT9O2iNpt5fNr1DHCvKuJdRjBLdqV5NHb+8+8AQy50mp" crossorigin="anonymous"></script>-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/2.0.8/js/dataTables.js"></script>

<script>
    $(document).ready( function () {
        $('#productsTable').DataTable({
            language: {
                info: "Mostrando _START_-_END_ de _TOTAL_ productos en total",
                infoEmpty: 'Lista de productos vacía',
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

