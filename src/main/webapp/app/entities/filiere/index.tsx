import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Filiere from './filiere';
import FiliereDetail from './filiere-detail';
import FiliereUpdate from './filiere-update';
import FiliereDeleteDialog from './filiere-delete-dialog';

const FiliereRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Filiere />} />
    <Route path="new" element={<FiliereUpdate />} />
    <Route path=":id">
      <Route index element={<FiliereDetail />} />
      <Route path="edit" element={<FiliereUpdate />} />
      <Route path="delete" element={<FiliereDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FiliereRoutes;
