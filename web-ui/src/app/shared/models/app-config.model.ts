export interface IAppConfig {
  urlServer: {
    api: string;
    oauth: string;
  };
  env: {
    name: string;
  };
  appInsights: {
    instrumentationKey: string;
  };
  logging: {
    console: boolean;
    appInsights: boolean;
  };
  aad: {
    requireAuth: boolean;
    tenant: string;
    clientId: string;
  };
}
