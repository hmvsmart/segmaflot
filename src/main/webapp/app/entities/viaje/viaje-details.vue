<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="viaje">
        <h2 class="jh-entity-heading" data-cy="viajeDetailsHeading">
          <span v-text="$t('segmaflotApp.viaje.detail.title')">Viaje</span> {{ viaje.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.viaje.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ viaje.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.fechaInicio')">Fecha Inicio</span>
          </dt>
          <dd>
            <span>{{ viaje.fechaInicio }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.fechaFin')">Fecha Fin</span>
          </dt>
          <dd>
            <span>{{ viaje.fechaFin }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.horaEmbarque')">Hora Embarque</span>
          </dt>
          <dd>
            <span v-if="viaje.horaEmbarque">{{ $d(Date.parse(viaje.horaEmbarque), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.tipoCarga')">Tipo Carga</span>
          </dt>
          <dd>
            <span>{{ viaje.tipoCarga }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.pesoNeto')">Peso Neto</span>
          </dt>
          <dd>
            <span>{{ viaje.pesoNeto }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.comentarios')">Comentarios</span>
          </dt>
          <dd>
            <div v-if="viaje.comentarios">
              <a v-on:click="openFile(viaje.comentariosContentType, viaje.comentarios)" v-text="$t('entity.action.open')">open</a>
              {{ viaje.comentariosContentType }}, {{ byteSize(viaje.comentarios) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.status')">Status</span>
          </dt>
          <dd>
            <span>{{ viaje.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ viaje.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="viaje.createdOn">{{ $d(Date.parse(viaje.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ viaje.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="viaje.modifiedOn">{{ $d(Date.parse(viaje.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ viaje.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.viaje.origen')">Origen</span>
          </dt>
          <dd>
            <div v-if="viaje.origen">
              <router-link :to="{ name: 'DireccionView', params: { direccionId: viaje.origen.id } }">{{ viaje.origen.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="viaje.id" :to="{ name: 'ViajeEdit', params: { viajeId: viaje.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./viaje-details.component.ts"></script>
