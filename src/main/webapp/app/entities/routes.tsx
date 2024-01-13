import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Carriere from './carriere';
import Filiere from './filiere';
import Cours from './cours';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="carriere/*" element={<Carriere />} />
        <Route path="filiere/*" element={<Filiere />} />
        <Route path="cours/*" element={<Cours />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
