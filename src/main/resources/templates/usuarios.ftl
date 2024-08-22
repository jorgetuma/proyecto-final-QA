<#include "layout.ftl">
<main class="container">
    <#if error??>
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
    </#if>

    <div class="d-flex justify-content-between align-items-center">
        <h3>Usuarios</h3>
        <button class="btn btn-primary m-3" data-bs-toggle="modal" data-bs-target="#createUserModal">Crear usuario</button>
    </div>

    <div class="table-responsive card p-3">
        <table class="table table-hover" id="usersTable">
            <thead>
            <tr>
                <th>Nombre de usuario</th>
                <th>Rol</th>
                <th></th>
            </tr>
            </thead>
            <tbody id="table">
            <#list usuarios as u>
                <tr>
                    <td>${u.userName}</td>
                    <td>${u.role}</td>
                    <td>
                        <button class="btn px-1 py-0" data-bs-toggle="modal" data-bs-target="#modifyUserModal-${u.id}">
                            <span class="material-symbols-outlined text-primary">edit</span>
                        </button>
                        <button class="btn px-1 py-0" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal-${u.id}">
                            <span class="material-symbols-outlined text-danger">delete</span>
                        </button>
                    </td>
                </tr>
                <!-- Modal para modificar usuario -->
                <div class="modal fade" id="modifyUserModal-${u.id}" tabindex="-1" role="dialog" aria-labelledby="modifyUserModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="modifyUserModalLabel">Modificar Usuario</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form class="needs-validation" id="modifyUserForm-${u.id}" method="post" action="/usuarios/editar/${u.id}" novalidate>
                                    <div class="mb-3">
                                        <label for="modifyUsername${u.id}" class="form-label">Nombre de usuario</label>
                                        <input type="text" class="form-control" name="username" id="modifyUsername${u.id}" value="${u.userName}" required>
                                        <div class="invalid-feedback">
                                            Por favor, ingrese un nombre de usuario.
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="modifyRol${u.id}" class="form-label">Rol</label>
                                        <select class="form-select" name="role" id="modifyRol${u.id}" required>
                                            <option value="ROLE_ADMIN" <#if u.role == 'ROLE_ADMIN'>selected</#if>>Administrador</option>
                                            <option value="ROLE_USER" <#if u.role == 'ROLE_USER'>selected</#if>>Usuario</option>
                                            <option value="ROLE_INVITADO" <#if u.role == 'ROLE_INVITADO'>selected</#if>>Invitado</option>
                                        </select>
                                        <div class="invalid-feedback">
                                            Por favor, seleccione un rol.
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary my-3" id="submit${u.id}">Modificar</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Modal para confirmar eliminación -->
                <div class="modal fade" id="confirmDeleteModal-${u.id}" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="confirmDeleteModalLabel">Confirmar Eliminación</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                ¿Estás seguro de que deseas eliminar este usuario?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                <a href="/usuarios/eliminar/${u.id}"><button type="button" class="btn btn-danger" id="confirmDeleteButton">Eliminar</button></a>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
            </tbody>
        </table>
    </div>

    <!-- Modal para crear usuario -->
    <div class="modal fade" id="createUserModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Crear Usuario</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="createUserForm" class="needs-validation" method="post" action="/usuarios/crear" novalidate>
                        <div class="mb-3">
                            <label for="username" class="form-label">Nombre de usuario</label>
                            <input type="text" class="form-control" name="username" id="username" required>
                            <div class="invalid-feedback">
                                Por favor, ingrese un nombre de usuario.
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Contraseña</label>
                            <input type="password" class="form-control" name="password" id="password" autocomplete="new-password" required>
                            <div class="invalid-feedback">
                                Por favor, ingrese una contraseña.
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="role" class="form-label">Rol</label>
                            <select class="form-select" name="role" id="role" required>
                                <option value="">Seleccione un rol</option>
                                <option value="ROLE_ADMIN">Administrador</option>
                                <option value="ROLE_USER">Usuario</option>
                                <option value="ROLE_INVITADO">Invitado</option>
                            </select>
                            <div class="invalid-feedback">
                                Por favor, seleccione un rol.
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/2.0.8/js/dataTables.js"></script>

<script>
    $(document).ready( function () {
        $('#usersTable').DataTable({
            language: {
                info: "Mostrando _START_-_END_ de _TOTAL_ usuarios en total",
                infoEmpty: 'Lista de usuarios vacía',
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
