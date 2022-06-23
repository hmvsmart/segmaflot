<template>
  <div>
    <h2 id="page-heading" data-cy="ProductoHeading">
      <span v-text="$t('segmaflotApp.producto.home.title')" id="producto-heading">Productos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.producto.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ProductoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-producto"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.producto.home.createLabel')"> Create a new Producto </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && productos && productos.length === 0">
      <span v-text="$t('segmaflotApp.producto.home.notFound')">No productos found</span>
    </div>
    <div class="table-responsive" v-if="productos && productos.length > 0">
      <table class="table table-striped" aria-describedby="productos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.nombre')">Nombre</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.marca')">Marca</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.tipo')">Tipo</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.cantidad')">Cantidad</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.unidadMedida')">Unidad Medida</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.material')">Material</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.color')">Color</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.uso')">Uso</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.descripcion')">Descripcion</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.otros')">Otros</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.producto.auditStatus')">Audit Status</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="producto in productos" :key="producto.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProductoView', params: { productoId: producto.id } }">{{ producto.id }}</router-link>
            </td>
            <td>{{ producto.nombre }}</td>
            <td>{{ producto.marca }}</td>
            <td>{{ producto.tipo }}</td>
            <td>{{ producto.cantidad }}</td>
            <td>{{ producto.unidadMedida }}</td>
            <td>{{ producto.material }}</td>
            <td>{{ producto.color }}</td>
            <td>{{ producto.uso }}</td>
            <td>
              <a
                v-if="producto.descripcion"
                v-on:click="openFile(producto.descripcionContentType, producto.descripcion)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="producto.descripcion">{{ producto.descripcionContentType }}, {{ byteSize(producto.descripcion) }}</span>
            </td>
            <td>
              <a v-if="producto.otros" v-on:click="openFile(producto.otrosContentType, producto.otros)" v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="producto.otros">{{ producto.otrosContentType }}, {{ byteSize(producto.otros) }}</span>
            </td>
            <td>{{ producto.createByUser }}</td>
            <td>{{ producto.createdOn ? $d(Date.parse(producto.createdOn), 'short') : '' }}</td>
            <td>{{ producto.modifyByUser }}</td>
            <td>{{ producto.modifiedOn ? $d(Date.parse(producto.modifiedOn), 'short') : '' }}</td>
            <td>{{ producto.auditStatus }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ProductoView', params: { productoId: producto.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ProductoEdit', params: { productoId: producto.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(producto)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="segmaflotApp.producto.delete.question" data-cy="productoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-producto-heading" v-text="$t('segmaflotApp.producto.delete.question', { id: removeId })">
          Are you sure you want to delete this Producto?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-producto"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeProducto()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./producto.component.ts"></script>
