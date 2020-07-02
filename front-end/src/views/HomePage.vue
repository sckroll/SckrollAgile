<template>
  <div>
    <page-header />
    <div class="boards-container">
      <div class="boards-section">
        <h2 class="section-title">
          {{ $t('homePage.personalBoards') }}
        </h2>
        <div class="boards d-flex align-content-start flex-wrap">
          <div
            class="board list-inline-item"
            v-for="board in personalBoards"
            :key="board.id"
            @click="openBoard(board)"
          >
            <h3>{{ board.name }}</h3>
            <p>{{ board.description }}</p>
          </div>
          <div
            class="board add list-inline-item"
            @click="createBoard()"
          >
            <font-awesome-icon icon="plus" />
            <div>{{ $t('homePage.createNewBoard') }}</div>
          </div>
        </div>
      </div>

      <div class="create-team-wrapper">
        <button class="btn btn-link" @click="createTeam()">
          + {{ $t('homePage.createNewTeam') }}
        </button>
      </div>
    </div>

    <create-board-modal
      :teamId="selectedTeamId"
      @created="onBoardCreated" />
    <create-team-modal />
    <!-- <h1 class="page-title">Home Page</h1> -->
  </div>
</template>

<script>
import $ from 'jquery'
import PageHeader from '@/components/PageHeader.vue'
import CreateBoardModal from '@/modals/CreateBoardModal.vue'
import CreateTeamModal from '@/modals/CreateTeamModal.vue'
import { mapGetters } from 'vuex'

export default {
  name: 'HomePage',
  components: {
    PageHeader,
    CreateBoardModal,
    CreateTeamModal
  },
  data() {
    return {
      selectedTeamId: 0
    }
  },
  computed: {
    ...mapGetters([
      'personalBoards',
      'teamBoards'
    ])
  },
  methods: {
    openBoard (board) {
      this.$router.push({ name: 'board', params: { boardId: board.id }})
    },
    createBoard (team) {
      this.selectedTeamId = team ? team.id : 0
      $('#createBoardModal').modal('show')
    },
    createTeam () {
      $('#createTeamModal').modal('show')
    },
    onBoardCreated (boardId) {
      this.$router.push({ name: 'board', params: { boardId }})
    }
  }
}
</script>

<style lang="scss" scoped>
.boards-container {
  padding: 0 35px;

  h2 {
    font-size: 18px;
    margin-bottom: 15px;
    font-weight: 400;
  }
  .boards-section {
    margin: 30px 10px;

    .boards {
      .board {
        width: 270px;
        height: 110px;
        border-radius: 5px;
        background-color: #377EF6;
        color: #fff;
        padding: 15px;
        margin-right: 10px;
        cursor: pointer;

        h3 {
          font-size: 16px;
        }
        p {
          line-height: 1.2;
          font-size: 90%;
          font-weight: 100;
          color: rgba(255, 255, 255, 0.70)
        }
      }
      .add {
        background-color: #f4f4f4;
        color: #666;
        text-align: center;
        padding-top: 30px;
        font-weight: 400;
      }
    }
  }
  .create-team-wrapper {
    .btn-link {
      color: #666;
      text-decoration: underline;
    }
  }
}
</style>
