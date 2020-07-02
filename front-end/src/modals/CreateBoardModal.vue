<template>
  <form @submit.prevent="saveBoard">
    <div
      class="modal"
      tabindex="-1"
      role="dialog"
      backdrop="static"
      id="createBoardModal"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">보드 생성</h5>
            <button
              type="button"
              class="close"
              @click="close"
              aria-level="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>

          <div class="modal-body">
            <div class="alert alert-danger failed" v-show="errorMessage">
              {{ errorMessage }}
            </div>
            <div class="form-group">
              <input
                type="text"
                class="form-control"
                id="boardNameInput"
                v-model="board.name"
                placeholder="보드 이름"
                maxlength="128">
              <div class="field-error" v-if="$v.board.name.$dirty">
                <div class="error" v-if="!$v.board.name.required">
                  보드 이름을 입력해주세요
                </div>
              </div>
            </div>
            <div class="form-group">
              <textarea
                class="form-control"
                v-model="board.description"
                placeholder="보드 설명"
              ></textarea>
              <div class="field-error" v-if="$v.board.description.$dirty">
                <div class="error" v-if="!$v.board.description.required">
                  보드 설명을 입력해주세요
                </div>
              </div>
            </div>
          </div>

          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">생성</button>
            <button
              type="button"
              class="btn btn-default btn-cancel"
              @click="close"
            >취소</button>
          </div>
        </div>
      </div>
    </div>
  </form>
</template>

<script>
import $ from 'jquery'
import { required } from 'vuelidate/lib/validators'
import boardService from '@/services/boards'

export default {
  name: 'CreatedBoardModal',
  props: ['teamId'],
  data() {
    return {
      board: {
        name: '',
        description: ''
      },
      errorMessage: ''
    }
  },
  validations: {
    board: {
      name: {
        required
      },
      description: {
        required
      }
    }
  },
  mounted() {
    $('#createBoardModal').on('shown.bs.modal', () => {
      $('#boardNameInput').trigger('focus')
    })
  },
  methods: {
    saveBoard () {
      this.$v.$touch()
      if (this.$v.$invalid) {
        return
      }

      const board = {
        teamId: this.teamId,
        name: this.board.name,
        description: this.board.description
      }

      boardService.create(board).then(createdBoard => {
        this.$store.dispatch('addBoard', createdBoard)
        this.$emit('created', createdBoard.id)
        this.close()
      }).catch(error => {
        this.errorMessage = error.message
      })
    },
    close () {
      this.$v.$reset()
      this.board.name = ''
      this.board.description = ''
      this.errorMessage = ''
      $('#createBoardModal').modal('hide')
    }
  }
}
</script>

<style lang="scss" scoped>
.modal {
  .modal-dialog {
    width: 400px;
  }
}
</style>
