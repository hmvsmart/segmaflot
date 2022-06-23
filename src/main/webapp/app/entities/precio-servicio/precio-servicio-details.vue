<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="precioServicio">
        <h2 class="jh-entity-heading" data-cy="precioServicioDetailsHeading">
          <span v-text="$t('segmaflotApp.precioServicio.detail.title')">PrecioServicio</span> {{ precioServicio.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.precioServicio.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ precioServicio.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioServicio.costo')">Costo</span>
          </dt>
          <dd>
            <span>{{ precioServicio.costo }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioServicio.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ precioServicio.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioServicio.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="precioServicio.createdOn">{{ $d(Date.parse(precioServicio.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioServicio.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ precioServicio.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioServicio.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="precioServicio.modifiedOn">{{ $d(Date.parse(precioServicio.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioServicio.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ precioServicio.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioServicio.servicio')">Servicio</span>
          </dt>
          <dd>
            <div v-if="precioServicio.servicio">
              <router-link :to="{ name: 'ServicioView', params: { servicioId: precioServicio.servicio.id } }">{{
                precioServicio.servicio.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="precioServicio.id"
          :to="{ name: 'PrecioServicioEdit', params: { precioServicioId: precioServicio.id } }"
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

<script lang="ts" src="./precio-servicio-details.component.ts"></script>
