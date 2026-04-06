import { Role } from "./role";

export interface Usuario {
    id?:number;
    telefone:string;
    email:string;
    nome:string;
    role:Role | null;
    cadastroIncompleto?: boolean;
    comercioProfileId?: number;

    urlFotoPerfil?: string;
}