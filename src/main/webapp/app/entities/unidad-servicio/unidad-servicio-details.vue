<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="unidadServicio">
        <h2 class="jh-entity-heading" data-cy="unidadServicioDetailsHeading">
          <span v-text="$t('segmaflotApp.unidadServicio.detail.title')">UnidadServicio</span> {{ unidadServicio.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.unidadServicio.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ unidadServicio.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadServicio.totalEstimado')">Total Estimado</span>
          </dt>
          <dd>
            <span>{{ unidadServicio.totalEstimado }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadServicio.totalReal')">Total Real</span>
          </dt>
          <dd>
            <span>{{ unidadServicio.totalReal }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadServicio.observacionesGenerales')">Observaciones Generales</span>
          </dt>
          <dd>
            <div v-if="unidadServicio.observacionesGenerales">
              <a
                v-on:click="openFile(unidadServicio.observacionesGeneralesContentType, unidadServicio.observacionesGenerales)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              {{ unidadServicio.observacionesGeneralesContentType }}, {{ byteSize(unidadServicio.observacionesGenerales) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadServicio.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ unidadServicio.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadServicio.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="unidadServicio.createdOn">{{ $d(Date.parse(unidadServicio.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadServicio.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ unidadServicio.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadServicio.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="unidadServicio.modifiedOn">{{ $d(Date.parse(unidadServicio.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadServicio.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ unidadServicio.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadServicio.cliente')">Cliente</span>
          </dt>
          <dd>
            <div v-if="unidadServicio.cliente">
              <router-link :to="{ name: 'ClienteView', params: { clienteId: unidadServicio.cliente.id } }">{{
                unidadServicio.cliente.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="unidadServicio.id"
          :to="{ name: 'UnidadServicioEdit', params: { unidadServicioId: unidadServicio.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./unidad-servicio-details.component.ts"></script>
