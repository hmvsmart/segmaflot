<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="diaHorario">
        <h2 class="jh-entity-heading" data-cy="diaHorarioDetailsHeading">
          <span v-text="$t('segmaflotApp.diaHorario.detail.title')">DiaHorario</span> {{ diaHorario.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.diaHorario.dia')">Dia</span>
          </dt>
          <dd>
            <span>{{ diaHorario.dia }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.diaHorario.horaEntrada')">Hora Entrada</span>
          </dt>
          <dd>
            <span v-if="diaHorario.horaEntrada">{{ $d(Date.parse(diaHorario.horaEntrada), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.diaHorario.horaSalida')">Hora Salida</span>
          </dt>
          <dd>
            <span v-if="diaHorario.horaSalida">{{ $d(Date.parse(diaHorario.horaSalida), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.diaHorario.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ diaHorario.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.diaHorario.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="diaHorario.createdOn">{{ $d(Date.parse(diaHorario.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.diaHorario.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ diaHorario.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.diaHorario.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="diaHorario.modifiedOn">{{ $d(Date.parse(diaHorario.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.diaHorario.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ diaHorario.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.diaHorario.horario')">Horario</span>
          </dt>
          <dd>
            <div v-if="diaHorario.horario">
              <router-link :to="{ name: 'HorarioView', params: { horarioId: diaHorario.horario.id } }">{{
                diaHorario.horario.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="diaHorario.id"
          :to="{ name: 'DiaHorarioEdit', params: { diaHorarioId: diaHorario.id } }"
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

<script lang="ts" src="./dia-horario-details.component.ts"></script>
