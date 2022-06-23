<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="statusCita">
        <h2 class="jh-entity-heading" data-cy="statusCitaDetailsHeading">
          <span v-text="$t('segmaflotApp.statusCita.detail.title')">StatusCita</span> {{ statusCita.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.statusCita.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ statusCita.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.statusCita.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('segmaflotApp.TipoStatusCita.' + statusCita.status)">{{ statusCita.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.statusCita.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ statusCita.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.statusCita.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="statusCita.createdOn">{{ $d(Date.parse(statusCita.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.statusCita.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ statusCita.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.statusCita.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="statusCita.modifiedOn">{{ $d(Date.parse(statusCita.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.statusCita.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ statusCita.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.statusCita.cita')">Cita</span>
          </dt>
          <dd>
            <div v-if="statusCita.cita">
              <router-link :to="{ name: 'CitaView', params: { citaId: statusCita.cita.id } }">{{ statusCita.cita.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="statusCita.id"
          :to="{ name: 'StatusCitaEdit', params: { statusCitaId: statusCita.id } }"
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

<script lang="ts" src="./status-cita-details.component.ts"></script>
