<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="unidad">
        <h2 class="jh-entity-heading" data-cy="unidadDetailsHeading">
          <span v-text="$t('segmaflotApp.unidad.detail.title')">Unidad</span> {{ unidad.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.unidad.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ unidad.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.numeroSerie')">Numero Serie</span>
          </dt>
          <dd>
            <span>{{ unidad.numeroSerie }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.kmActual')">Km Actual</span>
          </dt>
          <dd>
            <span>{{ unidad.kmActual }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.tipoAdquisicion')">Tipo Adquisicion</span>
          </dt>
          <dd>
            <span>{{ unidad.tipoAdquisicion }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.imagen')">Imagen</span>
          </dt>
          <dd>
            <div v-if="unidad.imagen">
              <a v-on:click="openFile(unidad.imagenContentType, unidad.imagen)" v-text="$t('entity.action.open')">open</a>
              {{ unidad.imagenContentType }}, {{ byteSize(unidad.imagen) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.color')">Color</span>
          </dt>
          <dd>
            <span>{{ unidad.color }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.descripcion')">Descripcion</span>
          </dt>
          <dd>
            <div v-if="unidad.descripcion">
              <a v-on:click="openFile(unidad.descripcionContentType, unidad.descripcion)" v-text="$t('entity.action.open')">open</a>
              {{ unidad.descripcionContentType }}, {{ byteSize(unidad.descripcion) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.status')">Status</span>
          </dt>
          <dd>
            <span>{{ unidad.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ unidad.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="unidad.createdOn">{{ $d(Date.parse(unidad.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ unidad.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="unidad.modifiedOn">{{ $d(Date.parse(unidad.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ unidad.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidad.vehiculo')">Vehiculo</span>
          </dt>
          <dd>
            <div v-if="unidad.vehiculo">
              <router-link :to="{ name: 'VehiculoView', params: { vehiculoId: unidad.vehiculo.id } }">{{ unidad.vehiculo.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="unidad.id" :to="{ name: 'UnidadEdit', params: { unidadId: unidad.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./unidad-details.component.ts"></script>
