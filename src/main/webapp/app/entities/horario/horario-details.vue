<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="horario">
        <h2 class="jh-entity-heading" data-cy="horarioDetailsHeading">
          <span v-text="$t('segmaflotApp.horario.detail.title')">Horario</span> {{ horario.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.horario.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ horario.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.horario.status')">Status</span>
          </dt>
          <dd>
            <span>{{ horario.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.horario.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ horario.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.horario.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="horario.createdOn">{{ $d(Date.parse(horario.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.horario.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ horario.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.horario.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="horario.modifiedOn">{{ $d(Date.parse(horario.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.horario.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ horario.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.horario.empleado')">Empleado</span>
          </dt>
          <dd>
            <div v-if="horario.empleado">
              <router-link :to="{ name: 'EmpleadoView', params: { empleadoId: horario.empleado.id } }">{{
                horario.empleado.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="horario.id" :to="{ name: 'HorarioEdit', params: { horarioId: horario.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./horario-details.component.ts"></script>
