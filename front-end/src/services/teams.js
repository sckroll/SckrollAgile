import axios from 'axios'
import errorParser from '@/utils/error-parser'

export default {
  /**
   * 새로운 팀 생성
   * @param {*} detail 팀의 세부사항
   */
  create (detail) {
    return new Promise((resolve, reject) => {
      axios.post('/teams', detail).then(({ data }) => {
        resolve(data)
      }).catch(error => {
        reject(errorParser.parse(error))
      })
    })
  }
}
