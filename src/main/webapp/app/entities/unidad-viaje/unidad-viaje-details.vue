<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="unidadViaje">
        <h2 class="jh-entity-heading" data-cy="unidadViajeDetailsHeading">
          <span v-text="$t('segmaflotApp.unidadViaje.detail.title')">UnidadViaje</span> {{ unidadViaje.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.unidadViaje.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ unidadViaje.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadViaje.status')">Status</span>
          </dt>
          <dd>
            <span>{{ unidadViaje.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadViaje.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ unidadViaje.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadViaje.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="unidadViaje.createdOn">{{ $d(Date.parse(unidadViaje.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadViaje.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ unidadViaje.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadViaje.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="unidadViaje.modifiedOn">{{ $d(Date.parse(unidadViaje.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadViaje.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ unidadViaje.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadViaje.viaje')">Viaje</span>
          </dt>
          <dd>
            <div v-if="unidadViaje.viaje">
              <router-link :to="{ name: 'ViajeView', params: { viajeId: unidadViaje.viaje.id } }">{{ unidadViaje.viaje.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.unidadViaje.unidad')">Unidad</span>
          </dt>
          <dd>
            <div v-if="unidadViaje.unidad">
              <router-link :to="{ name: 'UnidadView', params: { unidadId: unidadViaje.unidad.id } }">{{
                unidadViaje.unidad.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="unidadViaje.id"
          :to="{ name: 'UnidadViajeEdit', params: { unidadViajeId: unidadViaje.id } }"
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

<script lang="ts" src="./unidad-viaje-details.component.ts"></script>
