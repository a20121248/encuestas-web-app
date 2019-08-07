import { BrowserModule, Title } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

import { AppComponent } from './app.component';
import { UsuarioDatosComponent } from './shared/components/usuario-datos/usuario-datos.component';
import { JustificacionComponent } from './shared/components/justificacion/justificacion.component';
import { EmpresaComponent } from './modules/encuestas/components/empresa/empresa.component';
import { CentroComponent } from './modules/encuestas/components/centro/centro.component';
import { EpsComponent } from './modules/encuestas/components/eps/eps.component';
import { LineaCanalComponent } from './modules/encuestas/components/linea-canal/linea-canal.component';
import { ProductoCanalComponent } from './modules/encuestas/components/producto-canal/producto-canal.component';
import { ProductoSubcanalComponent } from './modules/encuestas/components/producto-subcanal/producto-subcanal.component';
import { RouterModule, Routes } from '@angular/router';
import { EncProductoSubcanalComponent } from './modules/encuestas/pages/enc-producto-subcanal/enc-producto-subcanal.component';
import { EncProductoCanalComponent } from './modules/encuestas/pages/enc-producto-canal/enc-producto-canal.component';
import { EncLineaCanalComponent } from './modules/encuestas/pages/enc-linea-canal/enc-linea-canal.component';

import { EncEmpresaComponent } from './modules/encuestas/pages/enc-empresa/enc-empresa.component';
import { EncEPSComponent } from './modules/encuestas/pages/enc-eps/enc-eps.component';
import { EncCentroComponent } from './modules/encuestas/pages/enc-centro/enc-centro.component';

import { AppConfig } from 'src/app/shared/services/app.config';
import { LoginComponent } from './modules/login/login.component';
import { NavegacionComponent } from './shared/components/navegacion/navegacion.component';
import { LineaComponent } from './modules/encuestas/components/linea/linea.component';
import { CanalComponent } from './modules/encuestas/components/canal/canal.component';
import { EncLineaComponent } from './modules/encuestas/pages/enc-linea/enc-linea.component';
import { EncCanalComponent } from './modules/encuestas/pages/enc-canal/enc-canal.component';
import { SeleccionarUsuarioComponent } from './modules/encuestas/pages/seleccionar-usuario/seleccionar-usuario.component';
import { CargarUsuariosComponent } from './modules/mantenimientos/components/cargar-usuarios/cargar-usuarios.component';
import { ProcesoComponent } from './modules/mantenimientos/components/proceso/proceso.component';
import { MantenimientosComponent } from './modules/mantenimientos/pages/mantenimientos/mantenimientos.component';
import { ReportesControlComponent } from './modules/reportes/components/reportes-control/reportes-control.component';
import { ReportesResultadosComponent } from './modules/reportes/components/reportes-resultados/reportes-resultados.component';
import { ReportesComponent } from './modules/reportes/pages/reportes/reportes.component';
import { TokenInterceptor } from './shared/interceptors/token.interceptor';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'colaboradores', pathMatch: 'full' },
  { path: 'colaboradores', component: SeleccionarUsuarioComponent },
  { path: 'colaboradores/:codigo/encuesta', component: EncEmpresaComponent },
  {
    path: 'colaboradores/:codigo/encuesta',
    children: [
      { path: 'eps', component: EncEPSComponent },
      { path: 'centro', component: EncCentroComponent },
      { path: 'linea-canal', component: EncLineaCanalComponent}, // perfil provincia
      { path: 'linea-canal',
        children: [
          { path: ':lineaId/:canalId/producto-subcanal', component: EncProductoSubcanalComponent } // perfil provincia
        ]
      },
      { path: 'lineas', component: EncLineaComponent },
      { path: 'lineas', // perfil varias-lineas o canal
        children: [
          { path: ':lineaId/producto-canal', component: EncProductoCanalComponent }, // perfil varias-lineas o una-linea
        ]
      }
    ]
  },
  { path: 'mantenimiento', component: MantenimientosComponent },
  { path: 'reporting', component: ReportesComponent }
];

/*const routes: Routes = [
  {
    path: 'encuestas',
    children: [
      { path: 'eps', component: EpsComponent },
      { path: 'centro', component: CentroComponent }
    ]
  }
];*/

export function initializeApp(appConfig: AppConfig) {
  return () => appConfig.load();
}
@NgModule({
  declarations: [
    AppComponent,
    UsuarioDatosComponent,
    JustificacionComponent,
    EmpresaComponent,
    EpsComponent,
    CentroComponent,
    LineaCanalComponent,
    ProductoCanalComponent,
    ProductoSubcanalComponent,
    EncProductoSubcanalComponent,
    EncProductoCanalComponent,
    EncLineaCanalComponent,
    EncCentroComponent,
    EncEmpresaComponent,
    EncEPSComponent,
    LoginComponent,
    NavegacionComponent,
    LineaComponent,
    CanalComponent,
    EncLineaComponent,
    EncCanalComponent,
    SeleccionarUsuarioComponent,
    CargarUsuariosComponent,
    ProcesoComponent,
    MantenimientosComponent,
    ReportesControlComponent,
    ReportesResultadosComponent,
    ReportesComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    FlexLayoutModule,
    FormsModule,
    FormsModule,
    HttpClientModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatSelectModule,
    MatSidenavModule,
    MatTableModule,
    MatTableModule,
    MatToolbarModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes),
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    Title,
    AppConfig,
    {
      provide: APP_INITIALIZER,
      useFactory: initializeApp,
      deps: [AppConfig], multi: true
    },
    MatDatepickerModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
