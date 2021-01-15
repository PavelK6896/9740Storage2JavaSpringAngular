import {environment} from "../../environments/environment.prod";

const storage = environment.DbUrl + '/storage2'
const client = storage + '/api/v1/client'

export const url1 = {
    login: storage + '/login',
    logout: storage + '/logout',
    getAll: client,
    deleteId: client,
    loadReportFile: storage + '/api/v1/file',
    update: client + '/update',
    filter: client + `/filter`,
    add: client + `/add`,
}
