<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cP">
        <h2 class="jh-entity-heading" data-cy="cPDetailsHeading">
          <span v-text="$t('segmaflotApp.cP.detail.title')">CP</span> {{ cP.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.cP.cp')">Cp</span>
          </dt>
          <dd>
            <span>{{ cP.cp }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cP.ciudad')">Ciudad</span>
          </dt>
          <dd>
            <div v-if="cP.ciudad">
              <router-link :to="{ name: 'CiudadView', params: { ciudadId: cP.ciudad.id } }">{{ cP.ciudad.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="cP.id" :to="{ name: 'CPEdit', params: { cPId: cP.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cp-details.component.ts"></script>
