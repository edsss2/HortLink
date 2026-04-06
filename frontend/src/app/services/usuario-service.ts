import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Usuario } from '../models/usuario.model';
import { Role } from '../models/role';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private readonly USER_STORAGE_KEY = 'currentUser';

  private currentUser: BehaviorSubject<Usuario | null>;
  
  // 1. Apenas DECLARE a variável aqui, não a inicialize.
  currentUser$: Observable<Usuario | null>;

  constructor() {
    let initialUser: Usuario | null = null;
    try {
      const storedUser = localStorage.getItem(this.USER_STORAGE_KEY);
      if (storedUser) {
        initialUser = JSON.parse(storedUser);
      }
    } catch (e) {
      console.error("Erro ao ler usuário do localStorage", e);
      localStorage.removeItem(this.USER_STORAGE_KEY);
    }

    // 2. INICIALIZE 'currentUser' primeiro.
    this.currentUser = new BehaviorSubject<Usuario | null>(initialUser);
    
    // 3. INICIALIZE 'currentUser$' agora, usando a variável que acabou de ser criada.
    this.currentUser$ = this.currentUser.asObservable();
  }

  /**
   * Pega o valor atual do usuário.
   */
  getCurrentUser(): Usuario | null {
    return this.currentUser.value;
  }

  /**
   * Atualiza o usuário logado, atualizando o BehaviorSubject
   * E o localStorage.
   */
  setCurrentUser(usuario: Usuario | null): void {
    if (usuario) {
      // 3. SALVA O USUÁRIO NO LOCALSTORAGE
      localStorage.setItem(this.USER_STORAGE_KEY, JSON.stringify(usuario));
    } else {
      // 4. REMOVE DO LOCALSTORAGE (no logout)
      localStorage.removeItem(this.USER_STORAGE_KEY);
    }
    
    // 5. ATUALIZA O BEHAVIORSUBJECT para notificar todos os subscribers
    this.currentUser.next(usuario);
  }

  /**
   * Verifica se o usuário logado tem permissão de vendedor.
   */
  public isVendedor(): boolean {
    const usuario = this.getCurrentUser();
    if (!usuario) {
      return false;
    }
    // Certifique-se de que a comparação de 'Role' está correta
    return usuario.role === Role.PRODUTOR || usuario.role === Role.COMERCIO;
  }

  /**
   * Retorna a foto do usuário ou uma imagem padrão de avatar.
   */
  public getFotoPerfilUrl(): string {
    const usuario = this.getCurrentUser();
    if (usuario && usuario.urlFotoPerfil) {
      return usuario.urlFotoPerfil;
    }
    return '/assets/perfil_not_found.png';
  }
}
