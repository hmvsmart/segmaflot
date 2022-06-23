<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cita">
        <h2 class="jh-entity-heading" data-cy="citaDetailsHeading">
          <span v-text="$t('segmaflotApp.cita.detail.title')">Cita</span> {{ cita.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.cita.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ cita.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cita.horaInicio')">Hora Inicio</span>
          </dt>
          <dd>
            <span v-if="cita.horaInicio">{{ $d(Date.parse(cita.horaInicio), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cita.horaFin')">Hora Fin</span>
          </dt>
          <dd>
            <span v-if="cita.horaFin">{{ $d(Date.parse(cita.horaFin), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cita.area')">Area</span>
          </dt>
          <dd>
            <span>{{ cita.area }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cita.identificador')">Identificador</span>
          </dt>
          <dd>
            <span>{{ cita.identificador }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cita.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ cita.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cita.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="cita.createdOn">{{ $d(Date.parse(cita.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cita.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ cita.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cita.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="cita.modifiedOn">{{ $d(Date.parse(cita.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cita.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ cita.auditStatus }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="cita.id" :to="{ name: 'CitaEdit', params: { citaId: cita.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cita-details.component.ts"></script>
