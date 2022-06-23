<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="producto">
        <h2 class="jh-entity-heading" data-cy="productoDetailsHeading">
          <span v-text="$t('segmaflotApp.producto.detail.title')">Producto</span> {{ producto.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.producto.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ producto.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.marca')">Marca</span>
          </dt>
          <dd>
            <span>{{ producto.marca }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.tipo')">Tipo</span>
          </dt>
          <dd>
            <span>{{ producto.tipo }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.cantidad')">Cantidad</span>
          </dt>
          <dd>
            <span>{{ producto.cantidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.unidadMedida')">Unidad Medida</span>
          </dt>
          <dd>
            <span>{{ producto.unidadMedida }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.material')">Material</span>
          </dt>
          <dd>
            <span>{{ producto.material }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.color')">Color</span>
          </dt>
          <dd>
            <span>{{ producto.color }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.uso')">Uso</span>
          </dt>
          <dd>
            <span>{{ producto.uso }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.descripcion')">Descripcion</span>
          </dt>
          <dd>
            <div v-if="producto.descripcion">
              <a v-on:click="openFile(producto.descripcionContentType, producto.descripcion)" v-text="$t('entity.action.open')">open</a>
              {{ producto.descripcionContentType }}, {{ byteSize(producto.descripcion) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.otros')">Otros</span>
          </dt>
          <dd>
            <div v-if="producto.otros">
              <a v-on:click="openFile(producto.otrosContentType, producto.otros)" v-text="$t('entity.action.open')">open</a>
              {{ producto.otrosContentType }}, {{ byteSize(producto.otros) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ producto.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="producto.createdOn">{{ $d(Date.parse(producto.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ producto.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="producto.modifiedOn">{{ $d(Date.parse(producto.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.producto.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ producto.auditStatus }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="producto.id" :to="{ name: 'ProductoEdit', params: { productoId: producto.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./producto-details.component.ts"></script>
