<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="reporte">
        <h2 class="jh-entity-heading" data-cy="reporteDetailsHeading">
          <span v-text="$t('segmaflotApp.reporte.detail.title')">Reporte</span> {{ reporte.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.reporte.fecha')">Fecha</span>
          </dt>
          <dd>
            <span v-if="reporte.fecha">{{ $d(Date.parse(reporte.fecha), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.reporte.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ reporte.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.reporte.documento')">Documento</span>
          </dt>
          <dd>
            <div v-if="reporte.documento">
              <a v-on:click="openFile(reporte.documentoContentType, reporte.documento)" v-text="$t('entity.action.open')">open</a>
              {{ reporte.documentoContentType }}, {{ byteSize(reporte.documento) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.reporte.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ reporte.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.reporte.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="reporte.createdOn">{{ $d(Date.parse(reporte.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.reporte.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ reporte.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.reporte.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="reporte.modifiedOn">{{ $d(Date.parse(reporte.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.reporte.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ reporte.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.reporte.empresa')">Empresa</span>
          </dt>
          <dd>
            <div v-if="reporte.empresa">
              <router-link :to="{ name: 'PersonaMoralView', params: { personaMoralId: reporte.empresa.id } }">{{
                reporte.empresa.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="reporte.id" :to="{ name: 'ReporteEdit', params: { reporteId: reporte.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./reporte-details.component.ts"></script>
