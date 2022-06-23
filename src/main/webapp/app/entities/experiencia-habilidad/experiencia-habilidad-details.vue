<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="experienciaHabilidad">
        <h2 class="jh-entity-heading" data-cy="experienciaHabilidadDetailsHeading">
          <span v-text="$t('segmaflotApp.experienciaHabilidad.detail.title')">ExperienciaHabilidad</span> {{ experienciaHabilidad.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.experienciaHabilidad.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ experienciaHabilidad.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.experienciaHabilidad.descripcion')">Descripcion</span>
          </dt>
          <dd>
            <div v-if="experienciaHabilidad.descripcion">
              <a
                v-on:click="openFile(experienciaHabilidad.descripcionContentType, experienciaHabilidad.descripcion)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              {{ experienciaHabilidad.descripcionContentType }}, {{ byteSize(experienciaHabilidad.descripcion) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.experienciaHabilidad.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ experienciaHabilidad.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.experienciaHabilidad.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="experienciaHabilidad.createdOn">{{ $d(Date.parse(experienciaHabilidad.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.experienciaHabilidad.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ experienciaHabilidad.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.experienciaHabilidad.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="experienciaHabilidad.modifiedOn">{{ $d(Date.parse(experienciaHabilidad.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.experienciaHabilidad.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ experienciaHabilidad.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.experienciaHabilidad.empleado')">Empleado</span>
          </dt>
          <dd>
            <div v-if="experienciaHabilidad.empleado">
              <router-link :to="{ name: 'EmpleadoView', params: { empleadoId: experienciaHabilidad.empleado.id } }">{{
                experienciaHabilidad.empleado.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="experienciaHabilidad.id"
          :to="{ name: 'ExperienciaHabilidadEdit', params: { experienciaHabilidadId: experienciaHabilidad.id } }"
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

<script lang="ts" src="./experiencia-habilidad-details.component.ts"></script>
