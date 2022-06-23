<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="itinerario">
        <h2 class="jh-entity-heading" data-cy="itinerarioDetailsHeading">
          <span v-text="$t('segmaflotApp.itinerario.detail.title')">Itinerario</span> {{ itinerario.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.itinerario.accion')">Accion</span>
          </dt>
          <dd>
            <span>{{ itinerario.accion }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.itinerario.fechaHoraEstimada')">Fecha Hora Estimada</span>
          </dt>
          <dd>
            <span v-if="itinerario.fechaHoraEstimada">{{ $d(Date.parse(itinerario.fechaHoraEstimada), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.itinerario.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ itinerario.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.itinerario.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="itinerario.createdOn">{{ $d(Date.parse(itinerario.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.itinerario.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ itinerario.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.itinerario.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="itinerario.modifiedOn">{{ $d(Date.parse(itinerario.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.itinerario.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ itinerario.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.itinerario.viaje')">Viaje</span>
          </dt>
          <dd>
            <div v-if="itinerario.viaje">
              <router-link :to="{ name: 'ViajeView', params: { viajeId: itinerario.viaje.id } }">{{ itinerario.viaje.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="itinerario.id"
          :to="{ name: 'ItinerarioEdit', params: { itinerarioId: itinerario.id } }"
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

<script lang="ts" src="./itinerario-details.component.ts"></script>
