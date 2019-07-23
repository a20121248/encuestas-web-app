import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatCardModule} from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatSelectModule} from '@angular/material/select';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


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
import { EncProductoSubCanalComponent } from './modules/encuestas/pages/enc-producto-subcanal/enc-producto-subcanal.component';
import { EncProductoCanalComponent } from './modules/encuestas/pages/enc-producto-canal/enc-producto-canal.component';
import { EncLineaCanalComponent } from './modules/encuestas/pages/enc-linea-canal/enc-linea-canal.component';

import { EncEmpresaComponent } from './modules/encuestas/pages/enc-empresa/enc-empresa.component';
import { EncEPSComponent } from './modules/encuestas/pages/enc-eps/enc-eps.component';
import { EncCentroComponent } from './modules/encuestas/pages/enc-centro/enc-centro.component';


import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table';
import { HttpClientModule } from '@angular/common/http';

import {AppConfig} from './shared/services/config/app.config';
import { LoginComponent } from './modules/login/login.component';
import { NavegacionComponent } from './shared/components/navegacion/navegacion.component';

const routes: Routes = [
  { path: '', redirectTo: 'encuestas', pathMatch: 'full' },
  { path: 'encuestas', component: EncEmpresaComponent },
  {
    path: 'encuestas',
    children: [
      { path: 'eps', component: EncEPSComponent },
      { path: 'centro', component: EncCentroComponent }
    ]
  },
  { path: 'login', component: LoginComponent }
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
    EncProductoSubCanalComponent,
    EncProductoCanalComponent,
    EncLineaCanalComponent,
    EncCentroComponent,
    EncEmpresaComponent,
    EncEPSComponent,
    LoginComponent,
    NavegacionComponent
  ],
  imports: [
    BrowserAnimationsModule,
    MatTableModule,
    BrowserModule,
    HttpClientModule,
    FormsModule,
    MatButtonModule,
    MatTableModule,
    MatInputModule,
    MatToolbarModule,
    MatCardModule,
    MatGridListModule,
    RouterModule.forRoot(routes),
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [ ],
  bootstrap: [AppComponent]
})
export class AppModule { }
