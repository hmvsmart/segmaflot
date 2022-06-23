<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="licenciaManejo">
        <h2 class="jh-entity-heading" data-cy="licenciaManejoDetailsHeading">
          <span v-text="$t('segmaflotApp.licenciaManejo.detail.title')">LicenciaManejo</span> {{ licenciaManejo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.licenciaManejo.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ licenciaManejo.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.licenciaManejo.tipo')">Tipo</span>
          </dt>
          <dd>
            <span>{{ licenciaManejo.tipo }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.licenciaManejo.status')">Status</span>
          </dt>
          <dd>
            <span>{{ licenciaManejo.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.licenciaManejo.fechaExpiracion')">Fecha Expiracion</span>
          </dt>
          <dd>
            <span>{{ licenciaManejo.fechaExpiracion }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.licenciaManejo.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ licenciaManejo.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.licenciaManejo.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="licenciaManejo.createdOn">{{ $d(Date.parse(licenciaManejo.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.licenciaManejo.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ licenciaManejo.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.licenciaManejo.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="licenciaManejo.modifiedOn">{{ $d(Date.parse(licenciaManejo.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.licenciaManejo.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ licenciaManejo.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.licenciaManejo.empleado')">Empleado</span>
          </dt>
          <dd>
            <div v-if="licenciaManejo.empleado">
              <router-link :to="{ name: 'EmpleadoView', params: { empleadoId: licenciaManejo.empleado.id } }">{{
                licenciaManejo.empleado.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="licenciaManejo.id"
          :to="{ name: 'LicenciaManejoEdit', params: { licenciaManejoId: licenciaManejo.id } }"
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

<script lang="ts" src="./licencia-manejo-details.component.ts"></script>
