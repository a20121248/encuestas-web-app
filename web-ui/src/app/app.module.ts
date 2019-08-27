import { BrowserModule, Title } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { MatRadioModule } from '@angular/material/radio';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatStepperModule } from '@angular/material/stepper';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDialogModule } from '@angular/material/dialog';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatBadgeModule } from '@angular/material/badge';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { ChartsModule } from 'ng2-charts';

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
import { SeleccionarUsuarioComponent } from './modules/encuestas/pages/seleccionar-usuario/seleccionar-usuario.component';
import { CargarUsuariosComponent } from './modules/mantenimientos/components/cargar-usuarios/cargar-usuarios.component';
import { ProcesoComponent } from './modules/mantenimientos/components/proceso/proceso.component';
import { MantenimientosComponent } from './modules/mantenimientos/pages/mantenimientos/mantenimientos.component';
import { ReportesComponent } from './modules/reportes/pages/reportes/reportes.component';
import { TokenInterceptor } from './shared/interceptors/token.interceptor';
import { ReporteControlComponent } from './modules/reportes/components/reporte-control/reporte-control.component';
import { ReporteEmpresasComponent } from './modules/reportes/components/reporte-empresas/reporte-empresas.component';
import { ReporteConsolidadoComponent } from './modules/reportes/components/reporte-consolidado/reporte-consolidado.component';
import { GraficoControlComponent } from './modules/reportes/components/grafico-control/grafico-control.component';
import { ResumenComponent } from './modules/resumen/pages/resumen/resumen.component';
import { GraficoCentroComponent } from './modules/resumen/components/grafico-centro/grafico-centro.component';
import { GraficoLineaComponent } from './modules/resumen/components/grafico-linea/grafico-linea.component';
import { UsuariosComponent } from './modules/mantenimientos/pages/usuarios/usuarios.component';
import { PosicionesComponent } from './modules/mantenimientos/pages/posiciones/posiciones.component';
import { PerfilesComponent } from './modules/mantenimientos/pages/perfiles/perfiles.component';
import { CentrosComponent } from './modules/mantenimientos/pages/centros/centros.component';
import { PosicionDatosComponent } from './modules/mantenimientos/pages/posicion-datos/posicion-datos.component';
import { AreasComponent } from './modules/mantenimientos/pages/areas/areas.component';
import { LineasComponent } from './modules/mantenimientos/pages/lineas/lineas.component';
import { CanalesComponent } from './modules/mantenimientos/pages/canales/canales.component';
import { ProductosComponent } from './modules/mantenimientos/pages/productos/productos.component';
import { SubcanalesComponent } from './modules/mantenimientos/pages/subcanales/subcanales.component';
import { CargarPerfilesComponent } from './modules/mantenimientos/components/cargar-perfiles/cargar-perfiles.component';
import { CargarPosicionesComponent } from './modules/mantenimientos/components/cargar-posiciones/cargar-posiciones.component';
import { CargarPosicionDatosComponent } from './modules/mantenimientos/components/cargar-posicion-datos/cargar-posicion-datos.component';
import { CargarCentrosComponent } from './modules/mantenimientos/components/cargar-centros/cargar-centros.component';
import { CargarAreasComponent } from './modules/mantenimientos/components/cargar-areas/cargar-areas.component';
import { FooterComponent } from './shared/components/footer/footer.component';
import { ModalCrearComponent } from './modules/mantenimientos/components/modal-crear/modal-crear.component';
import { ModalEditarComponent } from './modules/mantenimientos/components/modal-editar/modal-editar.component';
import { ModalEliminarComponent } from './modules/mantenimientos/components/modal-eliminar/modal-eliminar.component';
import { Page404Component } from './shared/components/error-pages/page404/page404.component';

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
  { path: 'resumen', component: ResumenComponent },
  { path: 'reporting', component: ReportesComponent },
  { path: 'mantenimiento', component: MantenimientosComponent },
  { path: 'mantenimiento',
    children: [
      { path: 'areas', component: AreasComponent },
      { path: 'centros-de-costos', component: CentrosComponent },
      { path: 'datos-posicion', component: PosicionDatosComponent },
      { path: 'lineas', component: LineasComponent },
      { path: 'canales', component: CanalesComponent },
      { path: 'productos', component: ProductosComponent },
      { path: 'subcanales', component: SubcanalesComponent },
      { path: 'perfiles', component: PerfilesComponent },
      { path: 'posiciones', component: PosicionesComponent },
      { path: 'usuarios', component: UsuariosComponent },
    ]
  },
  { path: '**', component: Page404Component}
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
    SeleccionarUsuarioComponent,
    CargarUsuariosComponent,
    ProcesoComponent,
    MantenimientosComponent,
    ReportesComponent,
    ReporteControlComponent,
    ReporteEmpresasComponent,
    ReporteConsolidadoComponent,
    GraficoControlComponent,
    ResumenComponent,
    GraficoCentroComponent,
    GraficoLineaComponent,
    UsuariosComponent,
    PosicionesComponent,
    PerfilesComponent,
    CentrosComponent,
    PosicionDatosComponent,
    AreasComponent,
    LineasComponent,
    CanalesComponent,
    ProductosComponent,
    SubcanalesComponent,
    CargarPerfilesComponent,
    CargarPosicionesComponent,
    CargarPosicionDatosComponent,
    CargarCentrosComponent,
    CargarAreasComponent,
    FooterComponent,
    ModalCrearComponent,
    ModalEditarComponent,
    ModalEliminarComponent,
    Page404Component
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    ChartsModule,
    FlexLayoutModule,
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
    NgSelectModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes),
    MatDatepickerModule,
    MatNativeDateModule,
    MatStepperModule,
    MatBadgeModule,
    MatRadioModule,
    MatDialogModule,
    MatMenuModule,
    MatProgressBarModule
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
  entryComponents: [
    ModalCrearComponent,
    ModalEditarComponent,
    ModalEliminarComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
